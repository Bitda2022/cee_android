package com.example.cee_project1.fregment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cee_project1.R
import com.example.cee_project1.activity.QuizActivity
import com.example.cee_project1.databinding.FragmentInvestBinding
import com.example.cee_project1.databinding.FragmentQuizSettingBinding

class QuizSettingFragment : Fragment() {

    lateinit var binding : FragmentQuizSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizSettingBinding.inflate(inflater, container, false)
        var quizType:String?=null



        binding.fragmentQuizSettingEconomyBasicBtn.setOnClickListener {
            val QuizIntent: Intent = Intent(activity,QuizActivity::class.java)
            QuizIntent.putExtra("quizType","economy_basic")
            quizType="economy_basic"
//            if(selectedFlag==false){
//                binding.fragmentQuizSettingEconomyBasicBtn.setBackgroundColor(context?.resources!!.getColor(R.color.end_blue))
//                selectedFlag=true
//                selectedBtn="economy_basic"
//            }

            //startActivity(QuizIntent)
        }

        binding.fragmentQuizSettingFinancialBasicBtn.setOnClickListener {

            val QuizIntent: Intent = Intent(activity,QuizActivity::class.java)
            QuizIntent.putExtra("quizType","financial_basic")
            quizType="financial_basic"
            binding.fragmentQuizSettingFinancialBasicBtn.setBackgroundColor(context?.resources!!.getColor(R.color.end_blue))
            //startActivity(QuizIntent)
        }
        binding.fragmentQuizSettingStockAdvancedBtn.setOnClickListener {

            val QuizIntent: Intent = Intent(activity,QuizActivity::class.java)
            QuizIntent.putExtra("quizType","stock_advanced")
            quizType="stock_advanced"
            binding.fragmentQuizSettingStockAdvancedBtn.setBackgroundColor(context?.resources!!.getColor(R.color.end_blue))
            //startActivity(QuizIntent)
        }

        binding.fragmentQuizSettingStartQuizBtn.setOnClickListener {
            val QuizIntent: Intent = Intent(activity,QuizActivity::class.java)
            QuizIntent.putExtra("quizType",quizType.toString())
            startActivity(QuizIntent)
        }

        return binding.root
    }



}