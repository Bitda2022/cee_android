package com.example.cee_project1.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cee_project1.R
import com.example.cee_project1.CEEApplication.Companion.tts
import com.example.cee_project1.databinding.FragmentSettingBinding
import com.example.cee_project1.service.TTSService


class SettingFragment : Fragment() {


    lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        eyespinner()
        // test tts code
//        testTTS()


        binding.setSpeedSk.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.settingProgressTv.text = progress.toString()
                val speed = (1f + (progress.toFloat() / 10))
                Log.d("speed", "onProgressChanged: $speed")
                tts.setSpeed(speed)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        return binding.root
    }


    private fun eyespinner() {
        val handicap = resources.getStringArray(R.array.handicap_level)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, handicap)
        binding.setEyeSp.adapter = adapter
    }




//    private fun testTTS() {
//        tts.setSpeed(1.3f)
//        tts.addContents("예시입니다")
//        //tts.addContents("만족을 얻으려고 생활에 필요한 재화나 서비스를 구매 또는 사용하는 행위")
//        //tts.addContents("컴퓨터 게임 프로그램을 산 것은 소비이지만, 유튜브 동영상을 만들기 위해서 동영상 편집 프로그램을 산 것은 소비가 아닙니다. " +
//                //"기계·장비·도구 등을 구입하는 것은 ‘최종적으로’ 사용해 다 써버리는 행위가 아니라, 다른 재화와 서비스를 효율적으로 생산하기 위한 수단으로 오랫동안 반복 사용하는 행위이기 때문에 경제학에서는 이를 ‘투자’라고 부릅니다.")
//        binding.root.setOnClickListener {
//            tts.execute()
//        }
////        binding.setContactTv.setOnClickListener {
////            tts.goBack()
////        }
////        binding.setSpeedIv.setOnClickListener {
////            tts.goForward()
////        }
////        tts.setOnDoneListener(
////            object : TTSService.OnDoneListener() {
////                override fun afterDone() {
////                    Log.d("tts", "afterDone: success")
////                    tts.addContents("소비")
////                    tts.addContents("만족을 얻으려고 생활에 필요한 재화나 서비스를 구매 또는 사용하는 행위")
////                    tts.addContents("컴퓨터 게임 프로그램을 산 것은 소비이지만, 유튜브 동영상을 만들기 위해서 동영상 편집 프로그램을 산 것은 소비가 아닙니다. " +
////                            "기계·장비·도구 등을 구입하는 것은 ‘최종적으로’ 사용해 다 써버리는 행위가 아니라, 다른 재화와 서비스를 효율적으로 생산하기 위한 수단으로 오랫동안 반복 사용하는 행위이기 때문에 경제학에서는 이를 ‘투자’라고 부릅니다.")
////                }
////            }
////        )
////        tts.pause()
////        tts.play()
////        tts.pause()
//
//    }



}
