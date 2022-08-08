package com.example.cee_project1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.databinding.ActivitySplashBinding
import com.example.cee_project1.databinding.ActivityTermBinding

class TermActivity : AppCompatActivity() {

    lateinit var binding : ActivityTermBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}