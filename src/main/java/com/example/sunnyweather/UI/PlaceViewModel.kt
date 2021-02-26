package com.example.sunnyweather.UI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.network.Repository
import java.util.ArrayList

class PlaceViewModel:ViewModel() {
    private val searchData = MutableLiveData<String>()

    val placelist = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchData){
            query-> Repository.searchPlace(query)}

    fun searchPlace(query:String){
        searchData.value = query
    }
}