package com.example.cee_project1.service

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.cee_project1.CEEApplication.Companion.prefs
import java.util.*
import kotlin.collections.ArrayList


class TTSService(context: Context) : UtteranceProgressListener(), TextToSpeech.OnInitListener {

    private var tts : TextToSpeech
    private var ttsNotice : TextToSpeech
    private var speed : Float = 1f
    private var contents = ArrayList<String>()
    private var alreadyRead = ArrayList<String>()
    private var nowIndex : Int = 0

    enum class State{ CLEAR, PLAY, PAUSE }

    private var state : State = State.CLEAR

    open class OnDoneListener {
        open fun afterDone() {
            return
        }
    }

    private var onDoneListener = OnDoneListener()

    init {
        tts = TextToSpeech(context, this, "com.google.android.tts")
        val engines = whatEnginesAreInstalled(context)
        for(e in engines) {
            Log.d("engine", "engines: $e")
        }

        ttsNotice = TextToSpeech(context, this, "com.google.android.tts")
    }

    fun setOnDoneListener(onDoneListener: OnDoneListener) {
        this.onDoneListener = onDoneListener
    }

    fun isEmpty() : Boolean {
        return contents.isEmpty()
    }

    fun reset() {
        tts.stop()
        speed = prefs.getFloat("tts_speed", 1f)
        contents.clear()
        nowIndex = 0
        state = State.CLEAR
        alreadyRead.clear()
    }

    fun setSpeed(speed : Float) {
        prefs.setFloat("tts_speed", speed)
        this.speed = speed
        tts.setSpeechRate(speed)
    }

    fun addContents(str : String) {
        contents.add("$str.")
    }

    fun readNotice(text : String, speed : Float) {
        ttsNotice.setSpeechRate(speed)
        ttsNotice.speak(text, TextToSpeech.QUEUE_FLUSH, null, "100")
    }

    fun play() {
        if(contents.isEmpty()) {
            Log.e("tts", "play: there is no contents!")
            return
        }
        if(state != State.PLAY) {
            state = State.PLAY
            tts.speak(contents[0], TextToSpeech.QUEUE_FLUSH, null, "-1")
        }
    }

    fun pause() {
        if(state == State.PLAY) {
            state = State.PAUSE
            tts.stop()
            contents[0] = contents[0].substring(nowIndex)
        }
    }

    fun goBack() : Boolean {
        if(alreadyRead.isEmpty() || contents.isEmpty())
            return false

        pause()

        if(contents[0].startsWith(alreadyRead.last()))
            alreadyRead.removeLast()
        if(alreadyRead.isNotEmpty()) {
            ttsNotice.setSpeechRate(3f)
            ttsNotice.speak(alreadyRead.last(), TextToSpeech.QUEUE_FLUSH, null, "-2")

            Log.d("tts", "goBack: speaking")
            contents[0] = alreadyRead.last() + contents[0]
            alreadyRead.removeLast()
        }

        return true
    }

    fun goForward() : Boolean {
        if(contents.isEmpty())
            return false

        pause()

        var idx = 0
        while(true) {
            if(idx >= contents[0].length - 1) {
                alreadyRead.add(contents[0])
                contents.removeAt(0)
                if(contents.isEmpty()) {
                    state = State.CLEAR
                    onDoneListener.afterDone()
                }
                nowIndex = 0
                break;
            } else if(contents[0][idx] == ' ' || contents[0][idx] == '.' || contents[0][idx] == ',') {
                alreadyRead.add(contents[0].substring(0, idx + 1))
                contents[0] = contents[0].substring(idx + 1)
                nowIndex = idx + 1
                break;
            }

            Log.d("tts", "goForward: " + contents[0][idx])
            idx++
        }
        ttsNotice.setSpeechRate(3f)
        ttsNotice.speak(alreadyRead.last(), TextToSpeech.QUEUE_FLUSH, null, "-3")

        return true
    }

    fun execute() {
        when (state) {
            State.PLAY -> {
                pause()
            }
            else -> {
                play()
            }
        }
    }

    override fun onInit(p0: Int) {
        if(p0 == TextToSpeech.SUCCESS) {
            tts.language = Locale.KOREA
            tts.setSpeechRate(speed)
            tts.setOnUtteranceProgressListener(this)

            ttsNotice.language = Locale.KOREA
        }
        else
            Log.e("tts", "onInit: failed to initialize")
    }

    override fun onStart(p0: String?) {
        Log.d("tts", "onStart: $p0")
        state = State.PLAY
    }

    override fun onDone(p0: String?) {
        Log.d("tts", "onDone: $p0")
        contents.removeAt(0)
        if(contents.isEmpty()) {
            state = State.CLEAR
            onDoneListener.afterDone()
            alreadyRead.clear()
            nowIndex = 0
        } else {
            tts.speak(contents[0], TextToSpeech.QUEUE_FLUSH, null, contents.size.toString())
        }
        nowIndex = 0
    }

    override fun onError(p0: String?) {
        Log.e("tts", "onError: $p0")
    }

    override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
        //Log.d("tts", "onRangeStart: $start / $end / $frame / $utteranceId / " + contents[0].substring(start, end))
        if(end != contents[0].length) {
            if (alreadyRead.isNotEmpty()) {
                if (alreadyRead.last() != contents[0].substring(start, end + 1))
                    alreadyRead.add(contents[0].substring(start, end + 1))
            } else {
                alreadyRead.add(contents[0].substring(start, end + 1))
            }
        }
        //Log.d("tts", "alreadyRead: $alreadyRead")
        nowIndex = start
    }

    private fun whatEnginesAreInstalled(context: Context): ArrayList<String> {
        val ttsIntent = Intent()
        ttsIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
        val pm = context.packageManager
        val list = pm.queryIntentActivities(ttsIntent, PackageManager.GET_META_DATA)
        val installedEngineNames: ArrayList<String> = ArrayList()
        for (r in list) {
            val engineName = r.activityInfo.applicationInfo.packageName
            installedEngineNames.add(engineName)

            // just logging the version number out of interest
            var version = "null"
            try {
                version = pm.getPackageInfo(
                    engineName,
                    PackageManager.GET_META_DATA
                ).versionName
            } catch (e: Exception) {
                Log.i("XXX", "try catch error")
            }
            Log.i("XXX", "we found an engine: $engineName")
            Log.i("XXX", "version: $version")
        }
        return installedEngineNames
    }

}