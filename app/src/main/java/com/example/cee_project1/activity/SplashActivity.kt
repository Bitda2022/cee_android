package com.example.cee_project1.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.CEEApplication.Companion.tts
import com.example.cee_project1.data.Event
import com.example.cee_project1.data.InvestOption
import com.example.cee_project1.data.Quiz
import com.example.cee_project1.data.Term
import com.example.cee_project1.databinding.ActivitySplashBinding
import com.example.cee_project1.service.DownloadData
import com.example.cee_project1.service.ManageInvestGame
import com.example.cee_project1.service.NetworkManager
import io.realm.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.where
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("splashActivity","실행")
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realm = Realm.getDefaultInstance()

        setInvestData(CEEApplication.gameManager)
        if(CEEApplication.prefs.getString("version", "null") == "null") {
            checkNetwork()
            setVersion()
            setDatabaseTermThenQuiz()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun checkNetwork() {
        Log.d("splashActivity","checkNetwork() 실행")
        // 네트워크가 연결되어 있지 않은 경우
        if (!NetworkManager.checkNetworkState(this)) {
            Log.d("chekNetworkState","네트워크 연결안됨")
//            tts.addContents("네트워크 연결을 확인해주세요")
        }
        // 네트워크가 연결되어 있는 경우
        else {
            Log.d("chekNetworkState","네트워크 연결됨")
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

    private fun setInvestData(manager : ManageInvestGame) {
        try {
            val fis = applicationContext.openFileInput("gameManager")
            val ois = ObjectInputStream(fis)
            CEEApplication.gameManager = ois.readObject() as ManageInvestGame
            ois.close()
            fis.close()
        } catch(e : Exception) {
            class MyHandler : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    val pair = msg.obj as Pair<*, *>
                    manager.setPlayerOptions(pair.first as ArrayList<InvestOption>)
                    manager.setEventsSequence(pair.second as ArrayList<ArrayList<Event>>)

                    val fos = applicationContext.openFileOutput("gameManager", Context.MODE_PRIVATE)
                    val oos = ObjectOutputStream(fos)
                    oos.writeObject(manager)
                    oos.close()
                    fos.close()
                    Log.d("invest", "handleMessage: complete")
                }
            }

            val handler = MyHandler()
            DownloadData().downloadInvestInfo(handler)
        }
    }

}