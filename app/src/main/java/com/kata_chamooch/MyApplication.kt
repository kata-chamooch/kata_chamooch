package com.kata_chamooch

import android.app.Application
import com.kata_chamooch.data.local.AppPreference

val prefs: AppPreference by lazy {
    MyApplication.prefs!!
}

class MyApplication : Application() {
    companion object {
        var prefs: AppPreference? = null
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = AppPreference(applicationContext)
    }
}