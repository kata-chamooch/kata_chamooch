package com.kata_chamooch.core

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateManager {
    private const val TAG = "dateTimeLogger"

    fun getTodayName(): String {
        val sdf = SimpleDateFormat("EEE", Locale.ENGLISH)
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)
        Log.d(TAG, "getTodayName: $dayOfTheWeek")

        return dayOfTheWeek.lowercase()
    }
}