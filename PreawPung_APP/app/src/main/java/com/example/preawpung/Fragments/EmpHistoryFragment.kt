package com.example.preawpung.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.EmpHisAdapter
import com.example.preawpung.DataClass.Order
import com.example.preawpung.databinding.FragmentEmpHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmpHistoryFragment : Fragment() {

    private lateinit var binding: FragmentEmpHistoryBinding
    var orderList = arrayListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmpHistoryBinding.inflate(layoutInflater)

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                orderList.clear()
                if (s.isNullOrBlank()) {

                } else {
                    callSearchOrderData(s.toString())
                }
            }
        })

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        orderList.clear()
        callOrderData()
    }

    override fun onPause() {
        super.onPause()
        binding.searchInput.text?.clear()
    }


    fun callOrderData() {

        orderList.clear()
        val api2: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api2.getAllOrder()
            .enqueue(object : Callback<List<Order>> {
                override fun onResponse(
                    call: Call<List<Order>>,
                    response: Response<List<Order>>
                ) {
                    response.body()?.forEach {
                        orderList.add(Order(it.order_id, it.slip_pic, it.order_date,it.order_status))
                    }
                    binding.recyclerHistory.adapter = EmpHisAdapter(orderList, requireContext())
                    binding.recyclerHistory.layoutManager = LinearLayoutManager(context)
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }


    fun callSearchOrderData(order_id: String) {

        orderList.clear()
        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.searchOrder(
            order_id.toString().toInt()
        )
            .enqueue(object : Callback<List<Order>> {
                override fun onResponse(
                    call: Call<List<Order>>,
                    response: Response<List<Order>>
                ) {
                    response.body()?.forEach {
                        orderList.add(Order(it.order_id, it.slip_pic, it.order_date, it.order_status))
                    }
                    binding.recyclerHistory.adapter = EmpHisAdapter(orderList, requireContext())
                    binding.recyclerHistory.layoutManager = LinearLayoutManager(context)
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }
}