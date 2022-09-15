package com.example.cee_project1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.CEEApplication.Companion.gameManager
import com.example.cee_project1.CEEApplication.Companion.tts
import com.example.cee_project1.R
import com.example.cee_project1.databinding.ActivityInvestFinalBinding
import com.example.cee_project1.databinding.ActivityInvestResultBinding

class InvestFinalActivity : AppCompatActivity() {
    lateinit var binding : ActivityInvestFinalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fragmentInvestSelectCompleteBtn.setOnClickListener {
            gameManager.resetGame()
            gameManager.saveState(applicationContext)
            finish()
        }

        initView()
    }

    private fun initView() {
        setResultImageView()
        setResultDescription()
    }

    private fun setResultImageView() {
        if(gameManager.getPlayerTotalMoney() < 80) { // 파멸의 손
            binding.activityInvestResultResultIv.setImageResource(R.drawable.invest_destroy)
            binding.activityInvestResultResultTv.text = "파멸의 손"
        } else if(gameManager.getPlayerTotalMoney() < 150) { // 주린이
            binding.activityInvestResultResultIv.setImageResource(R.drawable.invest_child)
            binding.activityInvestResultResultTv.text = "주린이"
        } else if(gameManager.getPlayerTotalMoney() < 250) { // 어엿한 개미
            binding.activityInvestResultResultIv.setImageResource(R.drawable.invest_ant)
            binding.activityInvestResultResultTv.text = "어엿한 개미"
        } else { // 주식의 신
            binding.activityInvestResultResultIv.setImageResource(R.drawable.invest_god)
            binding.activityInvestResultResultTv.text = "주식의 신"
        }
    }

    private fun setResultDescription() {
        binding.activityInvestResultMyCoinContentRoundTv.text = gameManager.getFinalResult()
    }

    override fun onBackPressed() {
        tts.readNotice("완료 버튼을 눌러 종료해주세요.", 1.3f)
    }
}