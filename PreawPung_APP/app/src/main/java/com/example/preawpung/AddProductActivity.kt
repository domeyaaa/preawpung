package com.example.preawpung

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.CartsAdapter
import com.example.preawpung.DataClass.*
import com.example.preawpung.databinding.ActivityAddProductBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddProductActivity : AppCompatActivity() {

    private lateinit var bindingAdd: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingAdd = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(bindingAdd.root)

        val account_id = intent.getStringExtra("account_id")
        val product_name = intent.getStringExtra("product_name")
        val product_pic = intent.getStringExtra("product_pic")
        val product_stock_amount = intent.getStringExtra("product_stock_amount")
        val product_detail = intent.getStringExtra("product_detail")
        val product_price = intent.getStringExtra("product_price")
        val product_id = intent.getStringExtra("product_id")

        bindingAdd.productId.text = "รหัสสินค้า " + product_id
        bindingAdd.productName.text = product_name
        bindingAdd.productPrice.text = "฿"+product_price
        bindingAdd.productPlus.text = product_detail
        if (product_stock_amount.toString().toInt() > 0) {
            bindingAdd.productStock.text = product_stock_amount
        }else{
            bindingAdd.productStock.text = "สินค้าหมด"
        }
        Glide.with(applicationContext).load(product_pic).into(bindingAdd.imgProduct)

        bindingAdd.backBtn.setOnClickListener() {
            finish()
        }

        bindingAdd.productPlus.movementMethod = ScrollingMovementMethod()

        //set button disable
        if (product_stock_amount.toString().toInt() == 0) {
            bindingAdd.addProductBtn?.isEnabled = false
            bindingAdd.addProductBtn?.setBackgroundColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.grey
                )
            )
            bindingAdd.productStock?.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.red
                )
            )
        }

        var orderID = mutableListOf<Any>()
        var orderLast = mutableListOf<Any>()
        orderID.add(0, "empty")
        bindingAdd.addProductBtn.setOnClickListener {
            val serv: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            serv.getCart(
                account_id.toString().toInt()
            ).enqueue(object : Callback<List<getCart>> {
                override fun onResponse(
                    call: Call<List<getCart>>,
                    response: Response<List<getCart>>
                ) {
                    response.body()?.forEach {
                        orderID.add(0, it.order_id.toString())
                    }
                    if (orderID[0] != "empty") {
                        val api: PreawpungAPI = Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:3000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(PreawpungAPI::class.java)
                        api.addCart(
                            orderID[0].toString().toInt(),
                            product_id.toString().toInt(),
                            product_price.toString().toFloat(),
                            1,
                        )
                            .enqueue(object : Callback<AddCart> {
                                override fun onResponse(
                                    call: Call<AddCart>,
                                    response: retrofit2.Response<AddCart>
                                ) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(
                                            applicationContext,
                                            "เพิ่มสินค้าสำเร็จ",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            applicationContext,
                                            "เพิ่มสินค้าไม่สำเร็จ",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<AddCart>, t: Throwable) {
                                    return t.printStackTrace()
                                }
                            })
                    } else {
                        Toast.makeText(applicationContext,"เกิดข้อผิดพลาด",Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<List<getCart>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
        }
    }
}


