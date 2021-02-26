package com.example.sunnyweather.logic.model

import com.example.sunnyweather.R

class Sky (val info:String,val icon:Int,val bg:Int)
private val sky = mapOf(
    "CLEAR_DAY" to Sky("晴",R.mipmap.hot,R.drawable.ic_launcher_foreground),
    "CLEAR_NIGHT" to Sky("晴",R.mipmap.cold,R.drawable.ic_launcher_background),
    "PARTLY_CLOUDY_DAY" to Sky("多云",R.mipmap.hot,R.drawable.ic_launcher_foreground),
    "CLOUDY" to Sky("阴",R.mipmap.cold,R.drawable.ic_launcher_background),
    "WIND" to Sky("大风",R.mipmap.hot,R.drawable.ic_launcher_foreground),
    "LIGHT_RAIN" to Sky("小雨",R.mipmap.hot,R.drawable.ic_launcher_background),
    "MODERATE_RAIN" to Sky("中雨",R.mipmap.hot,R.drawable.ic_launcher_foreground),
    "HEAVY_RAIN" to Sky("大雨",R.mipmap.hot,R.drawable.ic_launcher_background),
    "STORM_RAIN" to Sky("暴雨",R.mipmap.hot,R.drawable.ic_launcher_foreground),
    "THUNDER_SHOWER" to Sky("雷阵雨",R.mipmap.hot,R.drawable.ic_launcher_background)
)
fun getSky(skycon:String):Sky{
    return sky[skycon] ?: sky["CLEAR_DAY"]!!
}