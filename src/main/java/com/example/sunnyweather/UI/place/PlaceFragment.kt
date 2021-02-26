package com.example.sunnyweather.UI.place

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.sunnyweather.R
import com.example.sunnyweather.UI.PlaceViewModel
import com.example.sunnyweather.databinding.FragmentPlaceBinding
import com.example.sunnyweather.databinding.PlaceItemBinding

class PlaceFragment:Fragment() {
    private var _binding:FragmentPlaceBinding ?= null
    private val  binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerview.layoutManager = layoutManager
        adapter = PlaceAdapter(this,viewModel.placelist)
        binding.recyclerview.adapter = adapter
        binding.searchPlaceEdit.addTextChangedListener { editable ->

            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlace(content)
            } else {
                binding.recyclerview.visibility = View.GONE
                binding.bgImageView.visibility = View.VISIBLE
                viewModel.placelist.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            Log.d("this", "onActivityCreated: ")
            if (places != null ){
                binding.recyclerview.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE

                viewModel.placelist.clear()
                viewModel.placelist.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"no message",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }
}