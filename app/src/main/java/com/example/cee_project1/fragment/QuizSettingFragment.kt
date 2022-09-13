package com.example.cee_project1.fregment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.cee_project1.R
import com.example.cee_project1.activity.QuizActivity
import com.example.cee_project1.databinding.FragmentInvestBinding
import com.example.cee_project1.databinding.FragmentQuizSettingBinding

class QuizSettingFragment : Fragment() {

    lateinit var binding : FragmentQuizSettingBinding
    var btnFlagArr=ArrayList<Boolean>()
    var btnArr=ArrayList<Button>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizSettingBinding.inflate(inflater, container, false)
        var quizType:String?=null

        btnFlagArr.add(true)
        btnFlagArr.add(false)
        btnFlagArr.add(false)
        btnFlagArr.add(false)



        btnArr.add(binding.fragmentQuizSettingEconomyBasicBtn)
        btnArr.add(binding.fragmentQuizSettingFinancialBasicBtn)
        btnArr.add(binding.fragmentQuizSettingStockAdvancedBtn)
        btnArr.add(binding.fragmentQuizSettingCustomizeQuizBtn)


        for(i in 0..btnFlagArr.size-1)
            checkbox(i)

        binding.fragmentQuizSettingEconomyBasicBtn.setOnClickListener {
            checkbox(0)

            val QuizIntent: Intent = Intent(activity,QuizActivity::class.java)
            QuizIntent.putExtra("quizType","economy_basic")
            quizType="economy_basic"

        }

        binding.fragmentQuizSettingFinancialBasicBtn.setOnClickListener {
            checkbox(1)

            val QuizIntent: Intent = Intent(activity,QuizActivity::class.java)
            QuizIntent.putExtra("quizType","financial_basic")
            quizType="financial_basic"

        }
        binding.fragmentQuizSettingStockAdvancedBtn.setOnClickListener {
            checkbox(2)

            val QuizIntent: Intent = Intent(activity,QuizActivity::class.java)
            QuizIntent.putExtra("quizType","stock_advanced")
            quizType="stock_advanced"

        }

        binding.fragmentQuizSettingCustomizeQuizBtn.setOnClickListener {
            checkbox(3)

            val QuizIntent: Intent = Intent(activity,QuizActivity::class.java)
            QuizIntent.putExtra("quizType","customized_quiz")
            quizType="customized_quiz"

        }

        binding.fragmentQuizSettingStartQuizBtn.setOnClickListener {
            val QuizIntent: Intent = Intent(activity,QuizActivity::class.java)
            QuizIntent.putExtra("quizType",quizType.toString())
            startActivity(QuizIntent)
        }

        return binding.root
    }

    private fun checkbox(btnIdx:Int) {
        if(btnFlagArr[btnIdx]){//true(켜진상태)면 꺼지면 안됨

        }else {//btnFlagArr[btnIdx]==false면 true인 애 false로 바꾸고 얘를 true로
            for (i in 0..(btnFlagArr.size - 1)) {
                if (btnFlagArr[i]) {
                    btnFlagArr[i] = false
                }
            }
            btnFlagArr[btnIdx]=true
        }

        for(i in 0..(btnFlagArr.size-1)) {
            if (btnFlagArr[i])//true면 선택!
                btnArr[i].setBackgroundResource(R.drawable.button_rectangle_color)
            else//false면
                btnArr[i].setBackgroundResource(R.drawable.button_rectangle)
        }

    }


}