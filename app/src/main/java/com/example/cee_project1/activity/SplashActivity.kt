package com.example.cee_project1.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.data.Quiz
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ActivitySplashBinding
import com.example.cee_project1.service.DownloadData
import io.realm.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.where

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realm = Realm.getDefaultInstance()

        if(CEEApplication.prefs.getString("version", "null") == "null") {
            setVersion()
            setDatabaseTermThenQuiz()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setDatabaseTermThenQuiz() {
        class MyHandler : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val termList = msg.obj as ArrayList<Term>

                // apply past data
                for(term in termList) {
                    val tmpTerm: Term? = realm.where<Term>().contains("name", term.name).findFirst()
                    term.hasStudied = tmpTerm?.hasStudied ?: false
                }

                realm.beginTransaction()
                realm.delete<Term>()
                realm.insert(termList)
                realm.commitTransaction()

                setDatabaseQuiz()
            }
        }
        val handler = MyHandler()
        DownloadData().downloadTerms(handler)
    }

    private fun setDatabaseQuiz() {
        class MyHandler : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val quizList = msg.obj as ArrayList<Quiz>
                val removeList = ArrayList<Quiz>()

                for(quiz in quizList) {
                    val tmpQuiz: Quiz? = realm.where<Quiz>().contains("term", quiz.term).findFirst()
                    quiz.wrong = tmpQuiz?.wrong ?: 0

                    val term = realm.where<Term>().contains("name", quiz.term).findFirst()
                    realm.executeTransaction {
                        term?.quizs!!.add(quiz)
                    }
                    if(term != null)
                        removeList.add(quiz)

                    realm.executeTransaction {
                        tmpQuiz?.deleteFromRealm()
                    }
                }

                quizList.removeAll(removeList.toSet())

                realm.beginTransaction()
                realm.insert(quizList)
                realm.commitTransaction()

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        val handler = MyHandler()
        DownloadData().downloadQuizs(handler)
    }

    private fun setVersion() {
        class MyHandler : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val bundle = msg.data
                val version = bundle.getString("version")
                if(version != null)
                    CEEApplication.prefs.setString("version", version)
                else
                    CEEApplication.prefs.setString("version", "null")
            }
        }
        val handler = MyHandler()
        DownloadData().getVersion(handler)
    }

}