package com.example.cee_project1.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cee_project1.CEEApplication
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
        var optionsNameArr= CEEApplication.gameManager.getPlayersOptionsName()
        var i=0
        for(optionName in optionsNameArr){
            if(i==0)
                binding.activityInvestResultCompany1Tv.text=optionName+"\n"+CEEApplication.gameManager.getResults(optionName)
            else if(i==1)
                binding.activityInvestResultCompany2Tv.text=optionName+"\n"+CEEApplication.gameManager.getResults(optionName)
            else if(i==2)
                binding.activityInvestResultCompany3Tv.text=optionName+"\n"+CEEApplication.gameManager.getResults(optionName)
            Log.d("invest_test:투자 결과 apply 후", CEEApplication.gameManager.getResults(optionName)+"\n")
            i++
        }

        binding.fragmentInvestSelectCompleteBtn.setOnClickListener {
            btnFlag = true
            finish()
        }

        //나의 자본
        var myCapital=CEEApplication.gameManager.getPlayerTotalMoney()
        binding.activityInvestResultMyCoinContentRoundTv.text=myCapital.toString()
    }
}