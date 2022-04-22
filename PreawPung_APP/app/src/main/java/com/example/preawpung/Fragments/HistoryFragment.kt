package com.example.preawpung.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.CartsAdapter
import com.example.preawpung.Adapters.historysAdapter
import com.example.preawpung.DataClass.Cart
import com.example.preawpung.DataClass.History
import com.example.preawpung.R
import com.example.preawpung.databinding.FragmentHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryFragment : Fragment() {

    private lateinit var bindingHisFrag: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingHisFrag = FragmentHistoryBinding.inflate(layoutInflater)
        var historyList = arrayListOf<History>()
        var account_id = arguments?.getString("account_id")

        bindingHisFrag.recyclerHistory.adapter = historysAdapter(historyList, requireContext())
        bindingHisFrag.recyclerHistory.layoutManager = LinearLayoutManager(context)

        historyList.clear()
        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.gethistory(
            account_id.toString().toInt()
        )
            .enqueue(object : Callback<List<History>> {
                override fun onResponse(
                    call: Call<List<History>>,
                    response: Response<List<History>>
                ) {
                    response.body()?.forEach {
                        historyList.add(
                            History(
                                account_id.toString().toInt(),
                                it.order_id,
                                it.order_time,
                                it.order_status,
                                it.order_date,
                                it.order_total-50
                            )
                        )
                    }
                    bindingHisFrag.recyclerHistory.adapter =
                        historysAdapter(historyList, requireContext())
                    bindingHisFrag.recyclerHistory.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                override fun onFailure(call: Call<List<History>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })

        bindingHisFrag.swipe.setOnRefreshListener {
            var historyList = arrayListOf<History>()
            var account_id = arguments?.getString("account_id")

            bindingHisFrag.recyclerHistory.adapter = historysAdapter(historyList, requireContext())
            bindingHisFrag.recyclerHistory.layoutManager = LinearLayoutManager(context)

            historyList.clear()
            val api: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            api.gethistory(
                account_id.toString().toInt()
            )
                .enqueue(object : Callback<List<History>> {
                    override fun onResponse(
                        call: Call<List<History>>,
                        response: Response<List<History>>
                    ) {
                        response.body()?.forEach {
                            historyList.add(
                                History(
                                    account_id.toString().toInt(),
                                    it.order_id,
                                    it.order_time,
                                    it.order_status,
                                    it.order_date,
                                    it.order_total
                                )
                            )
                        }
                        bindingHisFrag.recyclerHistory.adapter =
                            historysAdapter(historyList, requireContext())
                        bindingHisFrag.recyclerHistory.layoutManager =
                            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    }

                    override fun onFailure(call: Call<List<History>>, t: Throwable) {
                        return t.printStackTrace()
                    }
                })
            bindingHisFrag.swipe.isRefreshing = false
        }

        return bindingHisFrag.root
    }
}