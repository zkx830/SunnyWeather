package com.example.sunnyweather.UI.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.model.Location
import com.example.sunnyweather.logic.network.Repository

class WeatherViewModel:ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()
    var locationlng = ""
    var  locationlat = ""
    var placename = ""
    val weatherLiveData = Transformations.switchMap(locationLiveData){location ->
        Repository.refreshWeather(location.lng,location.lat)
    }
    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value = Location(lng,lat)
    }

}