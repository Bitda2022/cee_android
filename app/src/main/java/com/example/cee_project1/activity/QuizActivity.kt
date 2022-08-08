package com.example.cee_project1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.databinding.ActivityQuizBinding
import com.example.cee_project1.databinding.ActivitySplashBinding

class QuizActivity : AppCompatActivity() {

    lateinit var binding : ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}