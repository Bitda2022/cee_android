package com.example.cee_project1.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cee_project1.R
import com.example.cee_project1.databinding.ActivityFinishQuizBinding
import com.example.cee_project1.databinding.ActivityInvestResultBinding
import kotlin.properties.Delegates

class InvestResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityInvestResultBinding
    var day: Int = 0

    companion object {
        var btnFlag: Boolean = false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        day++
        Log.d("investResultLifeCycle", "onCreate()호출")
        Log.d("investResultLifeCycle day : ", day.toString())
        initView()


    }

    private fun initView() {
        binding.fragmentInvestSelectCompleteBtn.setOnClickListener {
            btnFlag = true
            finish()
        }
    }
}