package com.example.cee_project1.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cee_project1.R
import com.example.cee_project1.activity.InvestResultActivity
import com.example.cee_project1.activity.InvestViewPagerActivity
import com.example.cee_project1.databinding.FragmentInvestBinding
import com.example.cee_project1.databinding.FragmentInvestSelectBinding


class InvestSelectFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding : FragmentInvestSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private fun initView() {
        binding.fragmentInvestSelectCompleteBtn.setOnClickListener {
            val intent = Intent(activity, InvestResultActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentInvestSelectBinding.inflate(inflater, container, false)

        initView()
        return binding.root
    }


}