package com.example.preawpung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.ProductDetailAdapter
import com.example.preawpung.DataClass.HisDetail
import com.example.preawpung.DataClass.ProductDetail
import com.example.preawpung.databinding.ActivityEmpHisDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmpHisDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEmpHisDetailBinding
    var productDetailList = arrayListOf<ProductDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmpHisDetailBinding.inflate(layoutInflater)

        val order_id = intent.getStringExtra("order_id")
        var slip_pic = mutableListOf<String>()

        productDetailList.clear()
        val serv: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        serv.getproductdetail(
            order_id.toString().toInt()
        )
            .enqueue(object : Callback<List<ProductDetail>> {
                override fun onResponse(
                    call: Call<List<ProductDetail>>,
                    response: Response<List<ProductDetail>>
                ) {
                    response.body()?.forEach {
                        productDetailList.add(
                            ProductDetail(
                                it.product_name,
                                it.product_pic,
                                it.product_amount,
                                it.product_price_tmm
                            )
                        )
                    }
                    binding.orderDetailView.adapter =
                        ProductDetailAdapter(productDetailList, applicationContext)
                    binding.orderDetailView.layoutManager = LinearLayoutManager(
                        applicationContext,
                        RecyclerView.HORIZONTAL, false
                    )
                }

                override fun onFailure(call: Call<List<ProductDetail>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })

        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.getHisDetail(
            order_id.toString().toInt()
        ).enqueue(object : Callback<HisDetail> {
            override fun onResponse(call: Call<HisDetail>, response: Response<HisDetail>) {
                slip_pic.add(0, response.body()?.slip_pic.toString())
                binding.accName.text = response.body()?.account_name
                binding.accTel.text = response.body()?.account_tel
                binding.accAddress.text = response.body()?.account_address
                binding.slipPrice.text = "฿" + response.body()?.slip_price.toString()
                binding.slipBank.text = response.body()?.slip_bank_name
                binding.slipBankNumber.text = response.body()?.slip_bank_number.toString()
                binding.slipDateTime.text = response.body()?.slip_date + " " + response.body()?.slip_time + " น."
                binding.orderTracking.text = response.body()?.order_tracking
            }
            override fun onFailure(call: Call<HisDetail>, t: Throwable) {
                println(t)
            }

        })

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.slipBtn.setOnClickListener {
            val i = Intent(applicationContext,ShowSlipActivity::class.java)
            i.putExtra("slip_pic",slip_pic[0].toString())
            startActivity(i)
        }

        binding.orderId.text = "หมายเลขคำสั่งซื้อ : "+order_id.toString()

        setContentView(binding.root)
    }
}