package com.example.cee_project1

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class CEEApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder().schemaVersion(1).allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(config)
    }

}