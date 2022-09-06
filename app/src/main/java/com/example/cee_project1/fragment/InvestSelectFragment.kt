package com.example.cee_project1.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.example.cee_project1.activity.InvestResultActivity
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
        changePage()
        return binding.root
    }

    private fun changePage() {
        binding.fragmentInvestSelectCompleteBtn.setOnClickListener {
            binding.fragmentInvestSelectCl.visibility=View.GONE

            binding.fragmentInvestSelectSelectedCompanyTv.visibility=View.VISIBLE
            binding.fragmentInvestSelectInvestedMoneyEt.visibility=View.VISIBLE
            binding.fragmentInvestSelectDeadlineBtn.visibility=View.VISIBLE


            val set = ConstraintSet()
            val constraintLayout = binding.root
            set.clone(constraintLayout)
            set.connect(
                binding.fragmentInvestSelectExsitingCoinTv.id,
                ConstraintSet.BOTTOM,
                binding.fragmentInvestSelectSelectedCompanyTv.id,
                ConstraintSet.TOP
            )
            set.applyTo(constraintLayout)
        }



        binding.fragmentInvestSelectDeadlineBtn.setOnClickListener {

            binding.fragmentInvestSelectSelectedCompanyTv.visibility = View.GONE
            binding.fragmentInvestSelectInvestedMoneyEt.visibility = View.GONE
            binding.fragmentInvestSelectDeadlineBtn.visibility = View.GONE

            binding.fragmentInvestSelectCl.visibility = View.VISIBLE

        }


    }


}