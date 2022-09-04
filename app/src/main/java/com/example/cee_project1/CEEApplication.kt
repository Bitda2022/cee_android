package com.example.cee_project1

import android.app.Application
import com.example.cee_project1.service.ManageInvestGame
import com.example.cee_project1.service.PreferenceUtil
import com.example.cee_project1.service.TTSService
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException

class CEEApplication : Application() {

    companion object {
        lateinit var prefs : PreferenceUtil
        lateinit var tts : TTSService
        var gameManager : ManageInvestGame = ManageInvestGame()
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)

        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder().schemaVersion(1).allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(config)
        try {
            val realm = Realm.getDefaultInstance()
        } catch (e: RealmMigrationNeededException) {
            Realm.deleteRealm(config)
            val realm = Realm.getDefaultInstance()
        }

        tts = TTSService(applicationContext)
        tts.reset()
    }

}