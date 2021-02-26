package com.example.sunnyweather.logic.network

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException

object Repository {
    fun refreshWeather(lng:String,lat:String) = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDailyResponse =async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDailyResponse.await()
                if (realtimeResponse.status=="ok" && dailyResponse.status=="ok") {
                    val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                     Result.success(weather)
                }else{
                    Result.failure(
                            RuntimeException("realtime response status is ${realtimeResponse.status}," +
                                    "daily response status is ${dailyResponse.status}")
                    )
                }
            }
        }catch (e:Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }
    fun searchPlace(query : String) = liveData(Dispatchers.IO) {
        val result = try {
            val PlaceResponse = SunnyWeatherNetwork.searchplace(query)
            if (PlaceResponse.status == "ok") {
                val places = PlaceResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${PlaceResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}