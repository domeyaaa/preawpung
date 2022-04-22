package com.example.preawpung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.ProductsAdapter
import com.example.preawpung.DataClass.Product
import com.example.preawpung.databinding.ActivityBangleBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BangleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBangleBinding
    private var productList = arrayListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBangleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }
        val account_id = intent.getStringExtra("account_id")

        productList.clear()
        val serv: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        serv.getBangle()
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

                    binding.recyclerView.adapter = ProductsAdapter(productList,applicationContext)
                    binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }
}