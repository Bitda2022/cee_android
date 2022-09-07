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
import io.realm.RealmList
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class QuizActivity : AppCompatActivity(){
//    DialogInterface.OnDismissListener

    lateinit var binding: ActivityQuizBinding

    lateinit var realm: Realm

    var totalQuizCnt:Int=0


    var quizIndex: Int = 0
    var flag: Boolean = false
    var correctCnt: Int = 0



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

        clickListener(quizs, quizIndex)
        Log.d("flag값", flag.toString())
        if (flag) {
            Log.d("flag값", "if(flag) 들어옴")
            settingQuiz(quizs, ++quizIndex)
        }

    }

    private fun clickListener(quizs: ArrayList<Quiz>, i: Int) {
        var i_index = i
        if(quizs.size==0){
            return
        }
        if (quizs.get(i).answer) {//정답이 O라면
            Log.d("TDialog","quiz index: $i")
            binding.activityQuizCorrectIv.setOnClickListener {
                Log.d("click_event", "정답 O인데 O 누름")

                //2초동안 보여주기
                val cDialog = CorrectAlertDialog(this) {}
                CoroutineScope(Main).launch {
                    cDialog.show()
                    delay(1000)
                    cDialog.dismiss()

                    if (i == totalQuizCnt-1) {
                        val intent = Intent(applicationContext, FinishQuizActivity::class.java)
                        intent.putExtra("correctCnt", correctCnt)
                        startActivity(intent)
                        finish()
                    }

                }



                correctCnt++
                flag = true

                Log.d("flag값", flag.toString())
                quiz(quizs)

            }
            binding.activityQuizWrongIv.setOnClickListener {
                Log.d("click_event", "정답 O인데 X 누름")


                val wDialog = WrongAlertDialog(this) {}
                val TDialog=TerminfoDialogFragment.newInstance(quizs.get(i).term)


                CoroutineScope(Main).launch {
                    wDialog.show()
                    delay(1000)
                    wDialog.dismiss()

//                    TerminfoDialogFragment.newInstance(quizs.get(i).term)
//                        ?.show(supportFragmentManager, "TerminfoDialogFragment")


                    TDialog?.show(supportFragmentManager, "TerminfoDialogFragment")
                    supportFragmentManager.executePendingTransactions()

                    if (i == totalQuizCnt-1) {
                        val intent = Intent(applicationContext, FinishQuizActivity::class.java)
                        intent.putExtra("correctCnt", correctCnt)
                        startActivity(intent)
                        finish()
                    }
                }

//
//                TDialog?.dialog?.setOnDismissListener(DialogInterface.OnDismissListener {
//                    //do whatever you want when dialog is dismissed
//                    Log.d("TDialog","onDismissListener 호출")
//                    Log.d("TDialog","i: $i")
//                    if (i == 9) {
//                        val intent = Intent(applicationContext, FinishQuizActivity::class.java)
//                        intent.putExtra("correctCnt", correctCnt)
//                        startActivity(intent)
//                        finish()
//                    }
//                })

                //wrong 횟수 증가시키기
                var termQuiz = realm.where<Quiz>().contains("term", quizs.get(i).term).findFirst()


                var presentWrongCnt = termQuiz?.wrong!!
                presentWrongCnt++


                realm.executeTransaction {
                    termQuiz?.wrong = presentWrongCnt
                }

                flag = true

                Log.d("flag값", flag.toString())
                quiz(quizs)
            }


        } else {//정답이 X라면
            binding.activityQuizCorrectIv.setOnClickListener {

                Log.d("click_event", "정답 X인데 O 누름")


//                TerminfoDialogFragment.newInstance(quizs.get(i).term)?.show(supportFragmentManager,"TerminfoDialogFragment")

                val wDialog = WrongAlertDialog(this) {}

                CoroutineScope(Main).launch {
                    wDialog.show()
                    delay(1000)
                    wDialog.dismiss()


                    val TiDialog=TerminfoDialogFragment.newInstance(quizs.get(i).term)
                            TiDialog?.show(supportFragmentManager, "TerminfoDialogFragment")



//                        ?.show(supportFragmentManager, "TerminfoDialogFragment")



                    if (i == totalQuizCnt-1) {
                        val intent = Intent(applicationContext, FinishQuizActivity::class.java)
                        intent.putExtra("correctCnt", correctCnt)
                        startActivity(intent)
                        finish()
                    }


                }


                flag = true

                Log.d("flag값", flag.toString())
                quiz(quizs)
            }
            binding.activityQuizWrongIv.setOnClickListener {
                Log.d("click_event", "정답 X인데 X 누름")

                //2초동안 보여주기
                val cDialog = CorrectAlertDialog(this) {}
                CoroutineScope(Main).launch {
                    cDialog.show()
                    delay(1000)
                    cDialog.dismiss()

                    if (i == totalQuizCnt-1) {
                        val intent = Intent(applicationContext, FinishQuizActivity::class.java)
                        intent.putExtra("correctCnt", correctCnt)
                        startActivity(intent)
                        finish()
                    }


                }

                correctCnt++
                flag = true

                Log.d("flag값", flag.toString())
                quiz(quizs)

            }


        }

    }

    private fun initData() {
        //initDatabase()
        realm = Realm.getDefaultInstance()

        val intent = intent
        val quizType = intent.getStringExtra("quizType")
        Log.d("Intent:quizType",quizType.toString())

        var termList: RealmResults<Term>?=null
        var quizList = ArrayList<Quiz>()

        when(quizType){
            "economy_basic"->{
                 termList = realm.where<Term>().contains("type","knowledge/economy_basic.html").findAll()

//                quizList=RealmResults<Quiz>(termList.size)
                if(termList!=null) {
                    for (term in termList) {
                        if(term?.quizs != null && term?.quizs!!.size != 0) {

                            quizList?.add(term?.quizs?.get(0)!!)
                        }
                    }
                }

                Log.d("quizType","economy_basic")

            }
            "financial_basic"->{
                termList = realm.where<Term>().contains("type","knowledge/financial_basic.html").findAll()
                if(termList!=null) {
                    for (term in termList) {
                        if(term?.quizs != null && term?.quizs!!.size != 0) {

                            quizList?.add(term?.quizs?.get(0)!!)
                        }
                    }
                }
                Log.d("quizType","financial_basic")

            }
            "stock_advanced"->{
                termList = realm.where<Term>().contains("type","knowledge/stock_advanced.html").findAll()
                Log.d("termListCnt",termList.size.toString())
                if(termList!=null) {
                    for (term in termList) {
                        if(term?.quizs != null && term?.quizs!!.size != 0) {
                            Log.d("termListCnt","quizList에 추가")
                            quizList?.add(term?.quizs?.get(0)!!)
                        }
                    }
                    Log.d("quizListCnt",quizList.size.toString())
                }
                Log.d("quizType","stock_advanced")
            }
            else->{

            }

        }



        var quizTen = ArrayList<Quiz>()
        var randIdxSet = mutableSetOf<Int>()

        //database에 있는 quizList 사이즈 만큼 인덱스 랜덤 추출(중복제거)
        if (quizList.size<10){
            totalQuizCnt=quizList.size
        }
        else{
            totalQuizCnt=10
        }

        while (randIdxSet.size < totalQuizCnt) {
            randIdxSet.add((0..quizList?.size!!.minus(1)).random(Random(System.currentTimeMillis())))
        }

        Log.d("quizListSize", quizList?.size.toString())
        Log.d("randIdxSet", randIdxSet.toString())


        var str2: String

        quizTen.clear()
        for (i in 0..totalQuizCnt-1) {



            val randIdx = randIdxSet.elementAt(i)

            val randQuiz = quizList?.get(randIdx)!!

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
        Log.d("quizTenSize", quizTen.size.toString())


        //quiz(quizs)
        if(totalQuizCnt!=0) {
            binding.activityQuizNumberTv.text = "퀴즈1"
            binding.activityQuizQuestionTv.text = quizTen[0].content
        }

        //realm에서 뽑은 DATA
        quiz(quizTen)

    }

    private fun settingQuiz(quizs: ArrayList<Quiz>, i: Int) {
        var quizNumber = i + 1
        var quizNumberText = "퀴즈" + quizNumber.toString()

//        if(i==10){
//            val intent = Intent(applicationContext, FinishQuizActivity::class.java)
//            intent.putExtra("correctCnt",correctCnt)
//            startActivity(intent)
//            finish()
//            return
//        }

        if(i==totalQuizCnt){
            return
        }

        binding.activityQuizNumberTv.text = quizNumberText
        Log.d("TAG", "settingQuiz: $i")
        binding.activityQuizQuestionTv.text = quizs.get(i).content
        flag = false
        quiz(quizs)
    }

//    override fun onDismiss(p0: DialogInterface?) {
//        TODO("Not yet implemented")
//    }


}

