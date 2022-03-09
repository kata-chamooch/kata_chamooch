package com.kata_chamooch.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class AppPreference(context: Context) {
    private val APP_DATABASE = "AppDatabase"

    companion object {
        const val MORNING_OFF_START_TIME = "morning-off-start-time"
        const val DINNER_OFF_START_TIME = "dinner-off-start-time"
        const val DINNER_OFF_BF = "dinner-bf"
        const val DINNER_OFF_LA = "dinner-la"
        const val DINNER_OFF_SN = "dinner-sn"
        const val DINNER_OFF_WO = "dinner-wo"

        const val MORNING_OFF_LA = "morning-la"
        const val MORNING_OFF_SN = "morning-sn"
        const val MORNING_OFF_DI = "morning-di"
        const val MORNING_OFF_WO = "morning-wo"
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

    fun getIntData(tag: String) = preferences.getInt(tag, -1)

    fun clearPref(tag: String) = preferences.edit().remove(tag).commit()
}