package com.example.cee_project1.service

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.cee_project1.CEEApplication.Companion.prefs
import java.util.*


class TTSService(context: Context) : UtteranceProgressListener(), TextToSpeech.OnInitListener {

    private var tts : TextToSpeech
    private var speed : Float = 1f
    private var contents = ArrayList<String>()
    private var nowIndex : Int = 0

    enum class State{ CLEAR, PLAY, PAUSE }

    private var state : State = State.CLEAR

    init {
        tts = TextToSpeech(context, this, "com.google.android.tts")
        val engines = whatEnginesAreInstalled(context)
        for(e in engines) {
            Log.d("engine", "engines: $e")
        }
    }

    fun reset() {
        speed = prefs.getFloat("tts_speed", 1f)
        contents.clear()
        nowIndex = 0
        state = State.CLEAR
    }

    fun setSpeed(speed : Float) {
        prefs.setFloat("tts_speed", speed)
        this.speed = speed
    }

    fun addContents(str : String) {
        contents.add(str)
    }

    fun play() {
        if(contents.isEmpty()) {
            Log.e("tts", "play: there is no contents!")
            return
        }
        if(state != State.PLAY) {
            state = State.PLAY
            contents[0] = contents[0].substring(nowIndex)
            tts.speak(contents[0], TextToSpeech.QUEUE_FLUSH, null, "1")
        }
    }

    fun pause() {
        state = State.PAUSE
        tts.stop()
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
        } else {
            tts.speak(contents[0], TextToSpeech.QUEUE_FLUSH, null, contents.size.toString())
        }
    }

    override fun onError(p0: String?) {
        Log.e("tts", "onError: $p0")
    }

    override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
        Log.d("tts", "onRangeStart: $start / $end / $frame / $utteranceId / " + contents[0].substring(start, end))
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