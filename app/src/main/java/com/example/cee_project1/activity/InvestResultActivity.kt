package com.example.cee_project1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cee_project1.R
import com.example.cee_project1.databinding.ActivityFinishQuizBinding
import com.example.cee_project1.databinding.ActivityInvestResultBinding
import kotlin.properties.Delegates

class InvestResultActivity : AppCompatActivity() {
    lateinit var binding : ActivityInvestResultBinding

    companion object {
        var btnFlag:Boolean =false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()


    }

    private fun initView() {
        binding.fragmentInvestSelectCompleteBtn.setOnClickListener {
            btnFlag=true
            finish()
        }
    }
}