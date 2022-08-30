package com.example.cee_project1.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.data.Quiz
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ActivityQuizBinding
import com.example.cee_project1.dialog.CorrectAlertDialog
import com.example.cee_project1.dialog.TerminfoDialogFragment
import com.example.cee_project1.dialog.WrongAlertDialog
import io.realm.Realm
import io.realm.kotlin.where
import kotlin.collections.ArrayList
import kotlin.random.Random


class QuizActivity : AppCompatActivity() {

    lateinit var binding : ActivityQuizBinding

    lateinit var realm:Realm

    var quizIndex:Int=0
    var flag:Boolean=false
    var correctCnt:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initDatabase()

    }

    private fun initDatabase() {

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
                CorrectAlertDialog(this) {

                }.show()
                correctCnt++
                flag=true

                Log.d("flag값",flag.toString())
                quiz(quizs)

            }
            binding.activityQuizWrongIv.setOnClickListener {
                Log.d("click_event","정답 O인데 X 누름")

                TerminfoDialogFragment.newInstance("send!!")?.show(supportFragmentManager,"TerminfoDialogFragment")

                WrongAlertDialog(this) {

                }.show()

                //wrong 횟수 증가시키기
                var termQuiz = realm.where<Quiz>().contains("term", quizs.get(i).term).findFirst()


                var presentWrongCnt=termQuiz?.wrong!!
                presentWrongCnt++


                realm.executeTransaction {
                    termQuiz?.wrong=presentWrongCnt
                }

                flag=true

                Log.d("flag값",flag.toString())
                quiz(quizs)
            }



        } else {//정답이 X라면
            binding.activityQuizCorrectIv.setOnClickListener {

                Log.d("click_event","정답 X인데 O 누름")


                TerminfoDialogFragment.newInstance(quizs.get(i).term)?.show(supportFragmentManager,"TerminfoDialogFragment")

                WrongAlertDialog(this) {

                }.show()
                val wad=WrongAlertDialog(this){}

                wad.show()



                //wad.cancel()

                flag=true

                Log.d("flag값",flag.toString())
                quiz(quizs)
            }
            binding.activityQuizWrongIv.setOnClickListener {
                Log.d("click_event","정답 X인데 X 누름")
                CorrectAlertDialog(this) {

                }.show()
                correctCnt++
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


        var quiz1=Quiz(0,"생산","아르바이트는 생산의 예이다",true,"해설1",0)
        var quiz2=Quiz(1,"분배","예금 이자를 받은 것은 생산의 예이다",false,"해설2",0)
        var quiz3=Quiz(2,"소비","유튜브 동영상을 만드릭 위해서 편집 프로그램을 산 것은 소비이다",false,"해설3",0)


        //initDatabase()
        realm = Realm.getDefaultInstance()
        val quizList = realm.where<Quiz>().findAll()
        var quizTen=ArrayList<Quiz>()
        var randIdxSet=mutableSetOf<Int>()

       //database에 있는 quizList 사이즈 만큼 인덱스 랜덤 추출(중복제거)
        while(randIdxSet.size<10){
            randIdxSet.add((0..quizList.size-1).random(Random(System.currentTimeMillis())))
        }

        Log.d("quizListSize",quizList.size.toString())
        Log.d("randIdxSet",randIdxSet.toString())



        var str2 : String
//        var index=0
        quizTen.clear()
        for(i in 0..9) {
//            if(i==10) {
//                return
//            }

            val randIdx=randIdxSet.elementAt(i)
            val randQuiz=quizList.get(randIdx)!!
            quizTen.add(randQuiz)
//            str2 = "\n-----------------------------------\n"
//            str2 += quizList.size.toString() + "\n"
//            str2 += "id : " + tmp.id + "\n"
            str2 = "term : " + randQuiz.term + "\n"
//            str2 += "content : " + tmp.content + "\n"
//            str2 += "answer : " + tmp.answer + "\n"
//            str2 += "commentary : " + tmp.commentary + "\n"
//            str2 += "wrong : " + tmp.wrong + "\n"
            Log.d("str2", "onCreate: quizs: $str2")
//            index++
        }
        Log.d("quizTenSize",quizTen.size.toString())





        //quiz(quizs)

        binding.activityQuizNumberTv.text = "퀴즈1"
        binding.activityQuizQuestionTv.text = quizTen[0].content

        //realm에서 뽑은 DATA
        quiz(quizTen)

    }

    private fun settingQuiz(quizs: ArrayList<Quiz>,i:Int) {
        var quizNumber=i+1
        var quizNumberText = "퀴즈" + quizNumber.toString()

        if(i==10){
            val intent = Intent(applicationContext, FinishQuizActivity::class.java)
            intent.putExtra("correctCnt",correctCnt)
            startActivity(intent)
            finish()
            return
        }

        binding.activityQuizNumberTv.text = quizNumberText
        Log.d("TAG", "settingQuiz: $i")
        binding.activityQuizQuestionTv.text = quizs.get(i).content
        flag = false
        quiz(quizs)
    }


}