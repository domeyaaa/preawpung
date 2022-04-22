package com.example.preawpung.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.ChecksAdapter
import com.example.preawpung.Adapters.TracksAdapter
import com.example.preawpung.DataClass.Check
import com.example.preawpung.DataClass.Track
import com.example.preawpung.R
import com.example.preawpung.databinding.FragmentEmpTrackBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EmpTrackFragment : Fragment()  {

    private lateinit var binding: FragmentEmpTrackBinding
    var trackList = arrayListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmpTrackBinding.inflate(layoutInflater)

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                trackList.clear()
                if (s.isNullOrBlank()) {

                } else {
                    callSearchTrackData(s.toString())
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        trackList.clear()
        callTrackData()
    }

    override fun onPause() {
        super.onPause()
        binding.searchInput.text?.clear()
    }


    fun callTrackData() {

        trackList.clear()
        val api2: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api2.getAllTrack()
            .enqueue(object : Callback<List<Track>> {
                override fun onResponse(
                    call: Call<List<Track>>,
                    response: Response<List<Track>>
                ) {
                    response.body()?.forEach {
                        trackList.add(Track(it.order_id, it.slip_pic, it.order_date,""))
                    }
                    binding.recyclerTrack.adapter = TracksAdapter(trackList, requireContext())
                    binding.recyclerTrack.layoutManager = LinearLayoutManager(context)
                }

                override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }

    fun callSearchTrackData(order_id: String) {

        trackList.clear()
        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.searchTrack(
            order_id.toString().toInt()
        )
            .enqueue(object : Callback<List<Check>> {
                override fun onResponse(
                    call: Call<List<Check>>,
                    response: Response<List<Check>>
                ) {
                    response.body()?.forEach {
                        trackList.add(Track(it.order_id, it.slip_pic, it.order_date,""))
                    }
                    binding.recyclerTrack.adapter = TracksAdapter(trackList, requireContext())
                    binding.recyclerTrack.layoutManager = LinearLayoutManager(context)
                }

                override fun onFailure(call: Call<List<Check>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }

}