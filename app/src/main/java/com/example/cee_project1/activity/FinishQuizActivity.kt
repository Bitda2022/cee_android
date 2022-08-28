package com.example.cee_project1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cee_project1.R
import com.example.cee_project1.databinding.ActivityFinishQuizBinding
import com.example.cee_project1.databinding.ActivityQuizBinding

class FinishQuizActivity : AppCompatActivity() {
    lateinit var binding : ActivityFinishQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showCorrectCnt()
        setButton()
    }

    private fun showCorrectCnt() {
        var correctCnt=intent.getIntExtra("correctCnt",0)
        var correctCntString="10문제중 ${correctCnt}문제 정답"

        binding.activityFinishQuizCorrectCntTv.text=correctCntString
    }

    private fun setButton() {
        binding.activityFinishQuizFinishQuizBtn.setOnClickListener {
            finish()
        }
    }


}