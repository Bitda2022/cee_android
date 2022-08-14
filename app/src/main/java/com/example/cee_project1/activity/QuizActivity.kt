package com.example.cee_project1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.cee_project1.databinding.ActivityQuizBinding
import com.example.cee_project1.databinding.ActivitySplashBinding
import com.example.cee_project1.dialog.WrongAlertDialog

class QuizActivity : AppCompatActivity() {

    lateinit var binding : ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        //임의로 O를 눌렀을 떄 다이얼로그 뜨도록 구현
        binding.activityQuizCorrectIv.setOnClickListener {
            WrongAlertDialog(this){

            }.show()
        }
    }

}