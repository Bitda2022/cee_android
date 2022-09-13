package com.example.cee_project1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.databinding.ActivityTermListBinding
import com.example.cee_project1.adapter.TermListVPAdapter
import com.google.android.material.tabs.TabLayoutMediator

class TermListActivity : AppCompatActivity() {

    lateinit var binding : ActivityTermListBinding
    private val title = arrayListOf("경제기초", "금융기반", "주식심화")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val termListVPAdapter = TermListVPAdapter(this)
        binding.termListViewPager.adapter = termListVPAdapter
        TabLayoutMediator(binding.termListTabLayout, binding.termListViewPager) {
            tab, position ->
            tab.text = title[position]
        }.attach()
    }

}