package com.example.cee_project1.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.activity.InvestViewPagerActivity
import com.example.cee_project1.databinding.FragmentInvestBinding

class InvestFragment : Fragment() {

    lateinit var binding : FragmentInvestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvestBinding.inflate(inflater, container, false)
        testInvestData()

        binding.investStartQuizBtn.setOnClickListener {
            val intent = Intent(activity, InvestViewPagerActivity::class.java)
            startActivity(intent)

        }

        return binding.root
    }

    private fun testInvestData() {
        binding.root.setOnClickListener {
            CEEApplication.gameManager!!.test()
        }
    }

}