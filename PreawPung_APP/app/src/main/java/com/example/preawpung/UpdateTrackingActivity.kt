package com.example.preawpung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.ProductDetailAdapter
import com.example.preawpung.DataClass.CheckDetail
import com.example.preawpung.DataClass.ProductDetail
import com.example.preawpung.DataClass.Track
import com.example.preawpung.databinding.ActivityUpdateTrackingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateTrackingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTrackingBinding
    var productDetailList = arrayListOf<ProductDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityUpdateTrackingBinding.inflate(layoutInflater)

        val order_id = intent.getStringExtra("order_id")

        binding.orderId.setText("หมายเลขคำสั่งซื้อ : "+order_id)

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
        api.getCheckDetail(
            order_id.toString().toInt()
        ).enqueue(object : Callback<CheckDetail> {
            override fun onResponse(call: Call<CheckDetail>, response: Response<CheckDetail>) {
                binding.accName.text = response.body()?.account_name
                binding.accTel.text = response.body()?.account_tel
                binding.accAddress.text = response.body()?.account_address
                binding.slipPrice.text = "฿" + response.body()?.slip_price.toString()
                binding.slipBank.text = response.body()?.slip_bank_name
                binding.slipBankNumber.text = response.body()?.slip_bank_number.toString()
                binding.slipDateTime.text = response.body()?.slip_date + " " + response.body()?.slip_time + " น."
            }

            override fun onFailure(call: Call<CheckDetail>, t: Throwable) {
                println(t)
            }

        })

        binding.backBtn.setOnClickListener{
            finish()
        }

        binding.confirmBtn.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this)
                    .inflate(R.layout.confirm_update_tracking_dialog_layout, null)
            val myBuilder = AlertDialog.Builder(this)
            myBuilder.setView(mDialogView)

            myBuilder.setPositiveButton("ยกเลิก") { dialog, which ->
                dialog.dismiss()
            }

            myBuilder.setNegativeButton("ยืนยัน") { dialog, which ->
                val order_tracking = binding.trackingEDT.text

                val api: PreawpungAPI = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PreawpungAPI::class.java)
                api.updateTracking(
                    order_id.toString().toInt(),
                    order_tracking.toString()
                ).enqueue(object : Callback<Track> {
                    override fun onResponse(call: Call<Track>, response: Response<Track>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "อัปเดตเลขแทร็กสำเร็จ",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "อัปเดตเลขแทร็กไม่สำเร็จ ลองอีกครั้ง",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Track>, t: Throwable) {
                        Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
            }
            myBuilder.show()
        }

        setContentView(binding.root)
    }
}