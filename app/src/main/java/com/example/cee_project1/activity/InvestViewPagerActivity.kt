package com.example.cee_project1.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
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
        binding = ActivityInvestViewPagerBinding.inflate(layoutInflater)
        fragmentList =ArrayList<Fragment>()
        fragmentList.add(InvestMainFragment())//게임 규칙 설명
        fragmentList.add(InvestMainFragment())//스토리
        fragmentList.add(InvestSelectFragment())//투자
        initAdapter()
        setContentView(binding.root)
    }

    override fun onResume() {
        Log.d("viewpager_lifecycle","investViewPagerActivity onResume 호출")
        if(InvestResultActivity.btnFlag==true){
            fragmentList.add(2,InvestMainFragment()) //이번 주차 스터리 추가
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