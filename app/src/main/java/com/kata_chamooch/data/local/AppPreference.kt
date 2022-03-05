package com.kata_chamooch.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class AppPreference(context: Context) {
    private val APP_DATABASE = "AppDatabase"

    companion object {
        val MORNING_OFF_START_TIME = "morning-off-start-time"
        val DINNER_OFF_START_TIME = "dinner-off-start-time"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(APP_DATABASE, Context.MODE_PRIVATE)

    fun setStringData(tag: String, value: String) {
        preferences.edit().putString(tag, value).apply()
        Log.d("Logger", "setStringData: saved")
    }

    fun getStringData(tag: String) = preferences.getString(tag, "")

    fun setIntData(tag: String, value: Int) {
        preferences.edit().putInt(tag, value).apply()
    }

    fun getIntData(tag: String) = preferences.getInt(tag, 0)

    fun clearPref(tag: String) = preferences.edit().remove(tag).commit()
}