package com.example.preawpung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.ProductsAdapter
import com.example.preawpung.Adapters.SearchAdapter
import com.example.preawpung.DataClass.Product
import com.example.preawpung.DataClass.ProductDetail
import com.example.preawpung.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import java.util.Locale.filter

class SearchActivity : AppCompatActivity() {

    private lateinit var bindingSearch: ActivitySearchBinding
    var productList = arrayListOf<Product>()
    var acc_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSearch = ActivitySearchBinding.inflate(layoutInflater)

        val account_id = intent.getStringExtra("account_id")
        acc_id = account_id.toString()
        bindingSearch.backBtn.setOnClickListener {
            finish()
        }

        bindingSearch.searchEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrBlank()) {
                    callProductdata(account_id.toString())
                }
                callSearchProductdata(s.toString(),acc_id.toString())
            }
        })
        setContentView(bindingSearch.root)
    }

    override fun onResume() {
        super.onResume()
        callProductdata(acc_id.toString())
    }


    fun callProductdata(account_id:String) {
        productList.clear()
        val serv: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        serv.retrieProduct()
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    response.body()?.forEach {
                        productList.add(
                            Product(
                                account_id.toString(),
                                it.product_id,
                                it.product_name,
                                it.product_price,
                                it.product_pic,
                                it.product_stock_amount,
                                it.product_detail
                            )
                        )
                    }
                    bindingSearch.searchView.adapter =
                        SearchAdapter(productList, applicationContext)
                    bindingSearch.searchView.layoutManager = LinearLayoutManager(applicationContext)
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })

    }

    fun callSearchProductdata(s: String,account_id:String) {
        productList.clear()
        val serv: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        serv.searchProduct(
            s
        )
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    response.body()?.forEach {
                        productList.add(
                            Product(
                                account_id.toString(),
                                it.product_id,
                                it.product_name,
                                it.product_price,
                                it.product_pic,
                                it.product_stock_amount,
                                it.product_detail
                            )
                        )
                    }
                    bindingSearch.searchView.adapter =
                        SearchAdapter(productList, applicationContext)
                    bindingSearch.searchView.layoutManager = LinearLayoutManager(applicationContext)
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })

    }
}