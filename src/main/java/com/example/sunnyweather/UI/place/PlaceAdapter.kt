package com.example.sunnyweather.UI.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R
import com.example.sunnyweather.UI.weather.WeatherActivity
import com.example.sunnyweather.logic.model.Place

class PlaceAdapter(private val fragment:Fragment,private val placelsit:List<Place>)
    : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val placename:TextView = itemView.findViewById(R.id.placename)
        val placeaddress:TextView = itemView.findViewById(R.id.placeadress)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
       val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val  place = placelsit[position]
            val intent = Intent(parent.context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            fragment.startActivity(intent)
            fragment.activity?.finish()
        }
        return holder
    }

    override fun getItemCount() = placelsit.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val palce = placelsit[position]
        holder.placename.text = palce.name
        holder.placeaddress.text = palce.adress
    }
}