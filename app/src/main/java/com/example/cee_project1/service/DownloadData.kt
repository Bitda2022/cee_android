package com.example.cee_project1.service

import android.os.Bundle
import android.os.Handler
import android.os.Message
import org.jsoup.Jsoup
import java.lang.Exception

class DownloadData {

    val baseURL = "https://bitda2022.github.io/cee_html/"

    fun getVersion(handler : Handler) {
        var version = ""

        Thread {
            kotlin.run {
                try {
                    var doc = Jsoup.connect(baseURL).get()
                    var verElement = doc.select("#version")

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

}