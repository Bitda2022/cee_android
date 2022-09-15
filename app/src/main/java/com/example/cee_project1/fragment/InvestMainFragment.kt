package com.example.cee_project1.fragment

import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cee_project1.CEEApplication.Companion.tts
import com.example.cee_project1.R
import com.example.cee_project1.databinding.FragmentInvestMainBinding
import com.example.cee_project1.service.TTSService


class InvestMainFragment(var stories:ArrayList<String>, val idx : Int) : Fragment() {

    lateinit var soundPool : SoundPool
    var d1 = -1
    var le = -1
    var mi = -1
    var pa = -1
    var sol = -1
    var isSoundPlay = false

    lateinit var binding : FragmentInvestMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        soundPool = SoundPool.Builder().build()
        d1 = soundPool.load(context, R.raw.low_do, 1)
        le = soundPool.load(context, R.raw.le, 1)
        mi = soundPool.load(context, R.raw.mi, 1)
        pa = soundPool.load(context, R.raw.pa, 1)
        sol = soundPool.load(context, R.raw.sol, 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInvestMainBinding.inflate(inflater, container, false)

        setStories()
        setGraphSoundListener()

        return binding.root
    }

    private fun setGraphSoundListener() {
        if(idx == 4){
            Log.d("sound pool", "setGraphSoundListener: true")
            binding.fragmentInvestMainContainerSv.setOnClickListener {
                playSound("자동차 기업")
            }
        }
    }

    private fun playSound(optionName : String) {
        Log.d("sound pool", "setGraphSoundListener: play")

        if(!isSoundPlay && optionName == "자동차 기업") {
            isSoundPlay = !isSoundPlay
            tts.reset()
            tts.addContents(optionName)
            tts.play()
            tts.setOnDoneListener(object : TTSService.OnDoneListener() {
                override fun afterDone() {
                    soundPool.play(d1, 1F, 1F, 0, 0, 1F)
                    Handler(Looper.getMainLooper()).postDelayed({
                        soundPool.play(le, 1F, 1F, 0, 0, 1F)
                        Handler(Looper.getMainLooper()).postDelayed({
                            soundPool.play(mi, 1F, 1F, 0, 0, 1F)
                            Handler(Looper.getMainLooper()).postDelayed({
                                soundPool.play(pa, 1F, 1F, 0, 0, 1F)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    soundPool.play(sol, 1F, 1F, 0, 0, 1F)
                                    playSound("문구 기업")
                                }, 500)
                            }, 500)
                        }, 500)
                    }, 500)
                }
            })
        } else if(optionName == "문구 기업") {
            tts.reset()
            tts.addContents(optionName)
            tts.play()
            tts.setOnDoneListener(object : TTSService.OnDoneListener() {
                override fun afterDone() {
                    soundPool.play(d1, 1F, 1F, 0, 0, 1F)
                    Handler(Looper.getMainLooper()).postDelayed({
                        soundPool.play(le, 1F, 1F, 0, 0, 1F)
                        Handler(Looper.getMainLooper()).postDelayed({
                            soundPool.play(mi, 1F, 1F, 0, 0, 1F)
                            Handler(Looper.getMainLooper()).postDelayed({
                                soundPool.play(le, 1F, 1F, 0, 0, 1F)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    soundPool.play(d1, 1F, 1F, 0, 0, 1F)
                                    playSound("제약 기업")
                                }, 500)
                            }, 500)
                        }, 500)
                    }, 500)
                }
            })
        } else if(optionName == "제약 기업") {
            tts.reset()
            tts.addContents(optionName)
            tts.play()
            tts.setOnDoneListener(object : TTSService.OnDoneListener() {
                override fun afterDone() {
                    soundPool.play(sol, 1F, 1F, 0, 0, 1F)
                    Handler(Looper.getMainLooper()).postDelayed({
                        soundPool.play(pa, 1F, 1F, 0, 0, 1F)
                        Handler(Looper.getMainLooper()).postDelayed({
                            soundPool.play(mi, 1F, 1F, 0, 0, 1F)
                            Handler(Looper.getMainLooper()).postDelayed({
                                soundPool.play(le, 1F, 1F, 0, 0, 1F)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    soundPool.play(d1, 1F, 1F, 0, 0, 1F)
                                    isSoundPlay = !isSoundPlay
                                }, 500)
                            }, 500)
                        }, 500)
                    }, 500)
                }
            })
        }
    }

    private fun setStories() {
        var storiesString=""
        for(story in stories){
            storiesString+=story
            storiesString+="\n"
        }
        binding.fragmentInvestMainStoriesTv.text=storiesString
        Log.d("invest_test:스토리",storiesString)
    }

}