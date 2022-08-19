package com.example.cee_project1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cee_project1.databinding.FragmentSettingBinding
import com.example.cee_project1.service.TTSService

class SettingFragment : Fragment() {

    lateinit var binding : FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        // test tts code
        val tts = TTSService(requireContext())
        tts.addContents("동해 물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세")
        tts.addContents("무궁화 삼천리 화려 강산 대한 사람 대한으로 길이 보전하세")
        tts.addContents("이 기상과 이 맘으로 충성을 다하여 괴로우나 즐거우나 나라 사랑하세")
        binding.root.setOnClickListener {
            tts.execute()
        }
        tts.reset()

        return binding.root
    }

}