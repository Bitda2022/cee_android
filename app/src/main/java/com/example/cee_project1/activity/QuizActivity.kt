package com.example.cee_project1.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.data.Quiz
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ActivityQuizBinding
import com.example.cee_project1.dialog.WrongAlertDialog

class QuizActivity : AppCompatActivity() {

    lateinit var binding : ActivityQuizBinding
    var quizIndex:Int=0
    var flag:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()

    }

    private fun quiz(quizs: ArrayList<Quiz>) {

        clickListener(quizs,quizIndex)
        Log.d("flag값",flag.toString())
        if(flag){
            Log.d("flag값","if(flag) 들어옴")
            settingQuiz(quizs,++quizIndex)
        }

    }

    private fun clickListener(quizs:ArrayList<Quiz>,i:Int){
        var i_index=i
        if(quizs.get(i).answer) {//정답이 O라면

            binding.activityQuizCorrectIv.setOnClickListener {
                Log.d("click_event","정답 O인데 O 누름")
                flag=true

                Log.d("flag값",flag.toString())
                quiz(quizs)

            }
            binding.activityQuizWrongIv.setOnClickListener {
                Log.d("click_event","정답 O인데 X 누름")

                WrongAlertDialog(this) {

                }.show()
                flag=true

                Log.d("flag값",flag.toString())
                quiz(quizs)
            }



        } else {//정답이 X라면
            binding.activityQuizCorrectIv.setOnClickListener {

                Log.d("click_event","정답 X인데 O 누름")
                WrongAlertDialog(this) {

                }.show()
                flag=true

                Log.d("flag값",flag.toString())
                quiz(quizs)
            }
            binding.activityQuizWrongIv.setOnClickListener {
                Log.d("click_event","정답 X인데 X 누름")

                flag=true

                Log.d("flag값",flag.toString())
                quiz(quizs)

            }



        }

    }

    private fun initData() {

        var term1= Term(1,
            "Economy_basic",
            "생산",
            "사람들에게 필요한 무언가를 만들어 내는 일을 말합니다.\n" +
                "        사람들에게 필요한 무언가는 재화(상품)와 용역(서비스)을 말합니다."
            ,""
            ,"예를 들어, 농부가 곡식을 키우는 일, 공장에서 물건을 만드는 일은 생산입니다.\n" +
                "            또한 병원에서의 의사의 치료 행위, 아르바이트도 예가 될 수 있습니다."
            ,false
            ,null)
        var term2= Term(2,"Economy_basic","분배","기업이 생산활동에 기여한 대가를 시장가격으로 보상해주는 것입니다." , "","  예금이자를 받은 것, 주식에 투자하여 배당금을 받은 것이 있습니다.\n" +
                "            여기서 주식 배당금이란 기업이 영업활동을 잘해서 이익이 남게 되면 그 회사 주식을 보유한 주주들에게 소유 지분에 따라 이윤을 분배하는 것입니다.",false,null)
        var term3= Term(3,"Economy_basic","소비","만족을 얻으려고 생활에 필요한 재화나 서비스를 구매 또는 사용하는 행위" , ""," 컴퓨터 게임 프로그램을 산 것은 소비이지만,\n" +
                "            유튜브 동영상을 만들기 위해서 동영상 편집 프로그램을 산 것은 소비가 아닙니다.\n" +
                "            기계·장비·도구 등을 구입하는 것은 ‘최종적으로’ 사용해 다 써버리는 행위가 아니라,\n" +
                "            다른 재화와 서비스를 효율적으로 생산하기 위한 수단으로 오랫동안 반복 사용하는 행위이기 때문에\n" +
                "            경제학에서는 이를 ‘투자’라고 부릅니다.",false,null)


        var quiz1=Quiz(0,"생산","아르바이트는 생산의 예이다",true,0)
        var quiz2=Quiz(1,"분배","예금 이자를 받은 것은 생산의 예이다",false,0)
        var quiz3=Quiz(2,"소비","유튜브 동영상을 만드릭 위해서 편집 프로그램을 산 것은 소비이다",false,0)

        var quizs=ArrayList<Quiz>()
        quizs.add(quiz1)
        quizs.add(quiz2)
        quizs.add(quiz3)


        //startQuiz(quizs)
        quiz(quizs)

    }

    private fun settingQuiz(quizs: ArrayList<Quiz>,i:Int) {
        var quizNumberText = "퀴즈" + i.toString()
        binding.activityQuizNumberTv.text = i.toString()
        Log.d("TAG", "settingQuiz: $i")
        binding.activityQuizQuestionTv.text = quizs.get(i).content
        flag = false
        quiz(quizs)
    }


}