package com.example.cee_project1.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.R

import com.example.cee_project1.databinding.ActivityInvestViewPagerBinding
import com.example.cee_project1.fragment.InvestMainFragment
import com.example.cee_project1.fragment.InvestSelectFragment

class InvestViewPagerActivity : AppCompatActivity() {
    lateinit var binding:ActivityInvestViewPagerBinding
    private lateinit var ViewPagerAdapter: InvestViewPagerAdapter

    lateinit var fragmentList:ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setGame()
        binding = ActivityInvestViewPagerBinding.inflate(layoutInflater)

        var rules=arrayOf("rule1","rule2","rule3")
        var stories=CEEApplication.gameManager.getNowEventsStory()

        fragmentList =ArrayList<Fragment>()
        fragmentList.add(InvestMainFragment(stories))//게임 규칙 설명
        fragmentList.add(InvestMainFragment(stories))//스토리
        fragmentList.add(InvestSelectFragment())//투자
        initAdapter()
        setContentView(binding.root)
    }

    private fun setGame() {
        CEEApplication.gameManager.resetGame()
        Log.d( "invest_test:현재주차: ",CEEApplication.gameManager.getNowSequence().toString())






    }

    override fun onResume() {
        Log.d("viewpager_lifecycle","investViewPagerActivity onResume 호출")
        if(InvestResultActivity.btnFlag==true){

            if(CEEApplication.gameManager.goNextSequence()){//주차 증가
                var stories=CEEApplication.gameManager.getNowEventsStory()
                fragmentList.add(fragmentList.size-1,InvestMainFragment(stories)) //이번 주차 스터리 추가
            }
            else{//7주차 -> 다음주차로 넘어가려고 할 때
                val intent = Intent(this, InvestFinalActivity::class.java)
                startActivity(intent)

            }


            Log.d( "invest_test:현재주차",CEEApplication.gameManager.getNowSequence().toString())
            var sequence=CEEApplication.gameManager.getNowSequence()
            initAdapter()
            InvestResultActivity.btnFlag=false
            binding.vpSample.currentItem=fragmentList.size-2

        }

        super.onResume()
    }
    private fun initAdapter() {
        //ViewPagerAdapter 초기화
        ViewPagerAdapter = InvestViewPagerAdapter(this)
        ViewPagerAdapter.fragments.addAll(fragmentList)

        //ViewPager2와 Adapter 연동
        binding.vpSample.adapter = ViewPagerAdapter

    }
}