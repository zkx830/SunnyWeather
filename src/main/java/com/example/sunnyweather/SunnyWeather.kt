package com.example.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeather: Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        const val TOKEN = "8OhvzpoNrQgoxKaC"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}