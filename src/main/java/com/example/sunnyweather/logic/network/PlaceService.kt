package com.example.sunnyweather.logic.network

import com.example.sunnyweather.SunnyWeather
import com.example.sunnyweather.logic.model.DailyResponse
import com.example.sunnyweather.logic.model.PlaceResponse
import com.example.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${SunnyWeather.TOKEN}&lang=zh_CN")
    fun searchplace(@Query("query") query: String):Call<PlaceResponse>
}