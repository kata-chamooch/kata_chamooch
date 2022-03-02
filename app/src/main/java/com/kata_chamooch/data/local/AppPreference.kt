package com.kata_chamooch.data.local

import android.content.Context
import android.content.SharedPreferences


class AppPreference(context: Context) {
    private val APP_DATABASE = "AppDatabase"
    private val MORNING_OFF_START_TIME = "morning-off-start-time"
    private val DINNER_OFF_START_TIME = "dinner-off-start-time"

    private val preferences: SharedPreferences =
        context.getSharedPreferences(APP_DATABASE, Context.MODE_PRIVATE)

    fun setStringData(tag: String, value: String) {
        preferences.edit().putString(tag, value).apply()
    }

    fun getStringData(tag: String) = preferences.getString(tag, "")

    fun setIntData(tag: String, value: Int) {
        preferences.edit().putInt(tag, value).apply()
    }

    fun getIntData(tag: String) = preferences.getInt(tag, 0)
}