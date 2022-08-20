package com.example.cee_project1

import android.app.Application
import com.example.cee_project1.service.PreferenceUtil
import com.example.cee_project1.service.TTSService
import io.realm.Realm
import io.realm.RealmConfiguration

class CEEApplication : Application() {

    companion object {
        lateinit var prefs : PreferenceUtil
        lateinit var tts : TTSService
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)

        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder().schemaVersion(1).allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(config)

        tts = TTSService(applicationContext)
        tts.reset()
    }

}