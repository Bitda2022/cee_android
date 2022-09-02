package com.example.cee_project1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cee_project1.R

import com.example.cee_project1.databinding.ActivityInvestViewPagerBinding
import com.example.cee_project1.fragment.InvestMainFragment
import com.example.cee_project1.fragment.InvestSelectFragment

class InvestViewPagerActivity : AppCompatActivity() {
    lateinit var binding:ActivityInvestViewPagerBinding
    private lateinit var ViewPagerAdapter: InvestViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestViewPagerBinding.inflate(layoutInflater)
        initAdapter()
        setContentView(binding.root)

    }
    private fun initAdapter() {
        //Adapter 안에 ViewPager2 상에 띄워줄 fragmentList 생성
        val fragmentList = listOf(InvestMainFragment(),InvestSelectFragment())

        //ViewPagerAdapter 초기화
        ViewPagerAdapter = InvestViewPagerAdapter(this)
        ViewPagerAdapter.fragments.addAll(fragmentList)

        //ViewPager2와 Adapter 연동
        binding.vpSample.adapter = ViewPagerAdapter
    }
}