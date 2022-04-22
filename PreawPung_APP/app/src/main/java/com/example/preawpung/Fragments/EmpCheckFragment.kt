package com.example.preawpung.Fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.ChecksAdapter
import com.example.preawpung.Adapters.historysAdapter
import com.example.preawpung.DataClass.Check
import com.example.preawpung.DataClass.GetTotal
import com.example.preawpung.DataClass.History
import com.example.preawpung.R
import com.example.preawpung.databinding.FragmentEmpCheckBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmpCheckFragment : Fragment() {
    private lateinit var binding: FragmentEmpCheckBinding
    var checkList = arrayListOf<Check>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmpCheckBinding.inflate(layoutInflater)

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                checkList.clear()
                if (s.isNullOrBlank()) {

                } else {
                    callSearchCheckData(s.toString())
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        callCheckData()
    }

    override fun onPause() {
        super.onPause()
        binding.searchInput.text?.clear()
    }


    fun callCheckData() {

        checkList.clear()
        val api2: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api2.getAllPaid()
            .enqueue(object : Callback<List<Check>> {
                override fun onResponse(
                    call: Call<List<Check>>,
                    response: Response<List<Check>>
                ) {
                    response.body()?.forEach {
                        checkList.add(Check(it.order_id, it.slip_pic, it.order_date))
                    }
                    binding.recyclerCheck.adapter = ChecksAdapter(checkList, requireContext())
                    binding.recyclerCheck.layoutManager = LinearLayoutManager(context)
                }

                override fun onFailure(call: Call<List<Check>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }

    fun callSearchCheckData(order_id: String) {

        checkList.clear()
        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.searchCheck(
            order_id.toString().toInt()
        )
            .enqueue(object : Callback<List<Check>> {
                override fun onResponse(
                    call: Call<List<Check>>,
                    response: Response<List<Check>>
                ) {
                    response.body()?.forEach {
                        checkList.add(Check(it.order_id, it.slip_pic, it.order_date))
                    }
                    binding.recyclerCheck.adapter = ChecksAdapter(checkList, requireContext())
                    binding.recyclerCheck.layoutManager = LinearLayoutManager(context)
                }

                override fun onFailure(call: Call<List<Check>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }

}