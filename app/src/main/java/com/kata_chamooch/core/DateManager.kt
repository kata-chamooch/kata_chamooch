package com.kata_chamooch.core

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateManager {
    private const val TAG = "dateTimeLogger"

    private val simpleDateFormat = SimpleDateFormat("dd/M/yyyy HH:mm:ss", Locale.UK)
    private val simpleDateFormatTwo = SimpleDateFormat("dd/M/yyyy", Locale.UK)

    fun getTodayPrefix(): String {
        val sdf = SimpleDateFormat("EEE", Locale.ENGLISH)
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)
        Log.d(TAG, "getTodayName: $dayOfTheWeek")

        return dayOfTheWeek.lowercase()
    }

    fun getTodayAsString(): String = simpleDateFormat.format(Date())
    fun getTodayDateAsString(): String = simpleDateFormatTwo.format(Date())
    fun getYesterdayDateString(): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        return simpleDateFormatTwo.format(calendar.time)
    }

    private fun getTodayAsDate(dateInString: String): Date? = simpleDateFormat.parse(dateInString)

    fun getDifference(startDateInString: String, endDateInString: String): Long {
        val startDate = getTodayAsDate(startDateInString)
        val endDate = getTodayAsDate(endDateInString)

        Log.d(TAG, "getDifference: $startDateInString $endDateInString")

        if (startDate != null && endDate != null) {
            var different = endDate.time - startDate.time
            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val elapsedDays = different / daysInMilli
            different %= daysInMilli
            val elapsedHours = different / hoursInMilli
            different %= hoursInMilli
            val elapsedMinutes = different / minutesInMilli
            different %= minutesInMilli
            val elapsedSeconds = different / secondsInMilli
            System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds
            )
            return if (elapsedDays > 1) {
                -1
            } else {
                val hourToMills = TimeUnit.HOURS.toMillis(elapsedHours)
                val minuteToMills = TimeUnit.MINUTES.toMillis(elapsedMinutes)
                val secToMills = TimeUnit.SECONDS.toMillis(elapsedSeconds)
                hourToMills + minuteToMills + secToMills
            }
        }
        return -1
    }

}