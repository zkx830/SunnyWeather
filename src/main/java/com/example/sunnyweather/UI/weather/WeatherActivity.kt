package com.example.sunnyweather.UI.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweather.R
import com.example.sunnyweather.databinding.ActivityMainBinding
import com.example.sunnyweather.databinding.ActivityWeatherBinding
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    private lateinit var bind:ActivityWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(bind.root)
        if (viewModel.locationlng.isEmpty()) {
            viewModel.locationlng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationlat.isEmpty()) {
            viewModel.locationlat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placename.isEmpty()) {
            viewModel.placename = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null){
                showWeatherInfo(weather)
            }else{
                Toast.makeText(this,"无法读取天气",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.locationlng,viewModel.locationlat)
    }

    private fun showWeatherInfo(weather: Weather) {
        bind.now.placeName.text = viewModel.placename
        val realtime = weather.realtime
        val daily = weather.daily
        //now.xml
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        bind.now.currentTemp.text =currentTempText
        bind.now.currentsky.text = getSky(realtime.skycon).info
        val currentPM25Text = "${realtime.airquality.aqi.chn.toInt()}"
        bind.now.currentAQI.text = currentPM25Text
        bind.now.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        //forecast.xml
        bind.forecast.forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days){
            val skycon = daily.skycon[i]
            val Tempture = daily.temperature[i]
            val  view = LayoutInflater.from(this).inflate(R.layout.forecast_item,bind.forecast.forecastLayout,false)
            val  dateInfo = view.findViewById(R.id.dateinfo) as TextView
            val skyIcon = view.findViewById(R.id.skyicon) as ImageView
            val  skyInfo = view.findViewById(R.id.skyinfo)as TextView
            val TemperatureInfo = view.findViewById(R.id.temperatureinfo)as TextView
            val simpleDataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDataFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val  tempText ="${Tempture.max.toInt()} ~ ${Tempture.min.toInt()} ℃"
            TemperatureInfo.text = tempText
            bind.forecast.forecastLayout.addView(view)
        }
        //life_index.xml
        val lifeIndex = daily.lifeindex
        bind.lifeindex.coldRiskText.text=lifeIndex.coldRisk[0].desc
        bind.lifeindex.carWashingText.text = lifeIndex.carWashing[0].desc
        bind.lifeindex.dressingText.text = lifeIndex.dressing[0].desc
        bind.lifeindex.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        bind.weatherLayout.visibility = View.VISIBLE
    }
}