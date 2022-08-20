package com.example.cee_project1.service

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context : Context) {

    private val prefs : SharedPreferences = context.getSharedPreferences("storage", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getFloat(key: String, defValue: Float): Float {
        return prefs.getFloat(key, defValue)
    }

    fun setFloat(key: String, float: Float) {
        prefs.edit().putFloat(key, float).apply()
    }

}