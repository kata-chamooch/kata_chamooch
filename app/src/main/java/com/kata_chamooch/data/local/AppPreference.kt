package com.kata_chamooch.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class AppPreference(context: Context) {
    private val APP_DATABASE = "AppDatabase"

    companion object {
        const val MORNING_OFF_START_TIME = "morning-off-start-time"
        const val DINNER_OFF_START_TIME = "dinner-off-start-time"
        const val DINNER_OFF_BF = "dinner-off-breakfast"
        const val DINNER_OFF_LA = "dinner-off-launch"
        const val DINNER_OFF_SN = "dinner-off-snacks"
        const val DINNER_OFF_WO = "dinner-off-workout"

        const val MORNING_OFF_LA = "morning-off-launch"
        const val MORNING_OFF_SN = "morning-off-snacks"
        const val MORNING_OFF_DI = "morning-off-dinner"
        const val MORNING_OFF_WO = "morning-off-workout"

        const val USER_SELECTED_METHOD = "selected-method"
        const val USER_TODAY_ITEM_POINT = "user-today-item-point"
        const val USER_TODAY_POINT = "user-today-point"
        const val USER_TOTAL_POINT = "user-total-point"
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