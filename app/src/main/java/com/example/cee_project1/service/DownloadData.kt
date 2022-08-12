package com.example.cee_project1.service

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.cee_project1.data.Quiz
import com.example.cee_project1.data.Term
import org.jsoup.Jsoup
import java.lang.Exception

class DownloadData {

    private val baseURL = "https://bitda2022.github.io/cee_html/"

    private val termURL = arrayOf("knowledge/economy_basic.html", "knowledge/financial_basic.html", "knowledge/stock_advanced.html")

    private val quizURL = arrayOf("quiz/economy_basic_quiz.html", "quiz/financial_basic_quiz.html", "quiz/stock_advanced_quiz.html")

    fun getVersion(handler : Handler) {
        var version = ""

        Thread {
            kotlin.run {
                try {
                    val doc = Jsoup.connect(baseURL).get()
                    val verElement = doc.select("#version")

                    if(!verElement.isEmpty())
                        version = verElement.text()

                    val bundle = Bundle()
                    bundle.putString("version", version)
                    val msg = handler.obtainMessage()
                    msg.data = bundle
                    handler.sendMessage(msg)
                } catch(e : Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    fun downloadTerms(handler : Handler) {
        var termList = ArrayList<Term>()

        Thread {
            kotlin.run {
                try {
                    for(url in termURL) {
                        val doc = Jsoup.connect(baseURL + url).get()
                        val forms = doc.select(".form")

                        for((id, form) in forms.withIndex()) {
                            val name = form.select(".term").text()
                            val description = form.select(".description").text()
                            val metaphor = form.select(".metaphor").text()
                            val example = form.select(".real_example").text()
                            val hasStudied = false
                            val quizs = ArrayList<Quiz>()

                            val term = Term(
                                id,
                                url,
                                name,
                                description,
                                metaphor,
                                example,
                                hasStudied,
                                quizs
                            )

                            termList.add(id, term)
                        }
                    }

                    val msg = handler.obtainMessage()
                    msg.obj = termList
                    handler.sendMessage(msg)
                } catch(e : Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

}