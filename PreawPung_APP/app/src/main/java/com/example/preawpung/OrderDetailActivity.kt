package com.example.preawpung

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.CartsAdapter
import com.example.preawpung.Adapters.ProductDetailAdapter
import com.example.preawpung.Adapters.ProductsAdapter
import com.example.preawpung.Adapters.SearchAdapter
import com.example.preawpung.DataClass.Order
import com.example.preawpung.DataClass.OrderDetail
import com.example.preawpung.DataClass.Product
import com.example.preawpung.DataClass.ProductDetail
import com.example.preawpung.databinding.ActivityAddProductBinding
import com.example.preawpung.databinding.ActivityOrderDetailBinding
import com.example.preawpung.databinding.ItemProductDetailLayoutBinding
import com.example.preawpung.databinding.ProductItemLayoutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Types.NULL

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    var productDetailList = arrayListOf<ProductDetail>()
    var orderDetailList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.backBtn.setOnClickListener() {
            finish()
        }

        val order_id = intent.getStringExtra("order_id")

        binding.btnCancel.setOnClickListener() {
            val mDialogView1 =
                LayoutInflater.from(this).inflate(R.layout.cancel_dialog_layout, null)
            val myBuilder1 = AlertDialog.Builder(this)
            myBuilder1.setView(mDialogView1)

            myBuilder1.setPositiveButton("ไม่") { dialog, which ->
                dialog.dismiss()
            }

            myBuilder1.setNegativeButton("ใช่") { dialog, which ->
                val serv: PreawpungAPI = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PreawpungAPI::class.java)
                serv.cancelOrder(
                    order_id.toString().toInt(),
                    "ok"
                ).enqueue(object : Callback<OrderDetail> {
                    override fun onResponse(
                        call: Call<OrderDetail>,
                        response: Response<OrderDetail>
                    ) {
                        if (response.isSuccessful()) {
                        }
                        finish()
                        Toast.makeText(
                            applicationContext,
                            "ยกเลิกคำสั่งซื้อสำเร็จ",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(call: Call<OrderDetail>, t: Throwable) {
                        return t.printStackTrace()
                    }
                })
            }
            myBuilder1.show()
        }

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


        //orderdetail
        productDetailList.clear()
        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.getorderdetail(
            order_id.toString().toInt()
        )
            .enqueue(object : Callback<List<OrderDetail>> {
                override fun onResponse(
                    call: Call<List<OrderDetail>>,
                    response: Response<List<OrderDetail>>
                ) {
                    response.body()?.forEach {
                        orderDetailList.add(0, it.account_name)
                        if (it.account_address.isNullOrBlank()) {
                            "ไม่ได้ระบุ"
                            orderDetailList.add(1, "ไม่ระบุที่อยู่")
                        } else {
                            orderDetailList.add(1, it.account_address)
                        }
                        if (it.account_tel.isNullOrBlank()) {
                            orderDetailList.add(2, "ไม่ระบุเบอร์โทร")
                        } else {
                            orderDetailList.add(2, it.account_tel)
                        }
                        orderDetailList.add(3, it.order_date)
                        orderDetailList.add(4, it.order_time)
                        orderDetailList.add(5, it.orderdetail_amount.toString())
                        orderDetailList.add(6, it.order_total.toString())
                        orderDetailList.add(7, it.order_id.toString())
                        if (it.order_tracking.isNullOrBlank()) {
                            orderDetailList.add(8, "")
                        } else {
                            orderDetailList.add(8, it.order_tracking)
                        }
                    }

                    var order_total = orderDetailList[6].toString().toFloat() + 50
                    binding.address.text =
                        orderDetailList[0].toString() + " | " + orderDetailList[2] + "\n" + orderDetailList[1]
                    binding.productPricemix.text = "฿" + orderDetailList[6]
                    binding.productNo.text = orderDetailList[7]
                    binding.productTime.text =
                        orderDetailList[3] + " " + orderDetailList[4] + " น."
                    binding.productPriceall.text = "฿" + order_total.toString()
                    binding.track.text = orderDetailList[8]
                }

                override fun onFailure(call: Call<List<OrderDetail>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })

        binding.btnPayment.setOnClickListener() {
            val i = Intent(this, SendSlipActivity::class.java)
            i.putExtra("order_id", order_id)
            var order_total = orderDetailList[6].toFloat() + 50
            i.putExtra("order_total", order_total.toString())
            startActivity(i)
        }

        binding.orderSuccess.setOnClickListener {
            val mDialogView2 =
                LayoutInflater.from(this).inflate(R.layout.ordersuccess_dialog_layout, null)
            val myBuilder2 = AlertDialog.Builder(this)
            myBuilder2.setView(mDialogView2)

            myBuilder2.setPositiveButton("ยกเลิก") { dialog, which ->
                dialog.dismiss()
            }

            myBuilder2.setNegativeButton("ยืนยัน") { dialog, which ->
                val api: PreawpungAPI = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PreawpungAPI::class.java)
                api.orderSuccess(
                    order_id.toString().toInt()
                ).enqueue(object : Callback<Order> {
                    override fun onResponse(call: Call<Order>, response: Response<Order>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "ยอมรับสินค้าสำเร็จ",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            finish()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "ทำรายการไม่สำเร็จ",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                    override fun onFailure(call: Call<Order>, t: Throwable) {
                        Toast.makeText(applicationContext, "ทำรายการไม่สำเร็จ!", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
            myBuilder2.show()
        }

        val order_status = intent.getStringExtra("order_status")

        if (order_status == "4") {
            binding.btnPayment?.isEnabled = false
            binding.btnPayment?.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
            binding.btnPayment?.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
            binding.btnCancel?.isEnabled = false
            binding.btnCancel?.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
            binding.btnCancel?.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
        }


        if (order_status == "2" || order_status == "3" || order_status == "6") {

            binding.btnPayment?.isEnabled = false
            binding.btnPayment?.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
            binding.btnPayment?.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
            binding.btnCancel?.isEnabled = false
            binding.btnCancel?.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
            binding.btnCancel?.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )

            binding.orderSuccess?.isEnabled = false
            binding.btnPayment?.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
            binding.orderSuccess?.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )

        }

    }
}