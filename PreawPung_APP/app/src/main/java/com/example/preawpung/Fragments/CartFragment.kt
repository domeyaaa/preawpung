package com.example.preawpung.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.CartsAdapter
import com.example.preawpung.DataClass.*
import com.example.preawpung.OrderDetailActivity
import com.example.preawpung.R
import com.example.preawpung.databinding.FragmentCartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class CartFragment : Fragment() {

    private lateinit var bindingCartFrag: FragmentCartBinding
    var cartList = arrayListOf<Cart>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingCartFrag = FragmentCartBinding.inflate(layoutInflater)


        var getAccount_id = arguments?.getString("account_id")
        var orderID = mutableListOf<String>()
        orderID.add(0, "0")


        val serv: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        serv.getCart(
            getAccount_id.toString().toInt()
        ).enqueue(object : Callback<List<getCart>> {
            override fun onResponse(
                call: Call<List<getCart>>,
                response: Response<List<getCart>>
            ) {
                response.body()?.forEach {
                    orderID.add(0, it.order_id.toString())
                }
                cartList.clear()
                val api: PreawpungAPI = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PreawpungAPI::class.java)
                api.cart(
                    orderID[0].toString()
                )
                    .enqueue(object : Callback<List<Cart>> {
                        override fun onResponse(
                            call: Call<List<Cart>>,
                            response: Response<List<Cart>>
                        ) {
                            response.body()?.forEach {
                                cartList.add(
                                    Cart(
                                        orderID[0].toInt(),
                                        it.product_id,
                                        it.product_name,
                                        it.product_pic,
                                        it.product_detail,
                                        it.product_price_tmm,
                                        it.product_amount,
                                        it.sum,
                                    )
                                )
                            }
                            bindingCartFrag.basketRecycler.adapter =
                                CartsAdapter(cartList, requireContext())
                            bindingCartFrag.basketRecycler.layoutManager =
                                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

                        }

                        override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                            return t.printStackTrace()
                        }
                    })


                var total = mutableListOf<String>()
                val api2: PreawpungAPI = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PreawpungAPI::class.java)
                api2.getTotal(
                    orderID[0].toString()
                )
                    .enqueue(object : Callback<List<GetTotal>> {
                        override fun onResponse(
                            call: Call<List<GetTotal>>,
                            response: Response<List<GetTotal>>
                        ) {
                            response.body()?.forEach {
                                total.add(0, it.total.toString())
                            }

                            bindingCartFrag.sumPrice.text = "รวมทั้งหมด ฿" + total[0]
                            if (total[0] == "0.0") {
                                bindingCartFrag.confirmBtn?.isEnabled = false
                                bindingCartFrag.confirmBtn?.setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.grey
                                    )
                                )
                            }
                        }

                        override fun onFailure(call: Call<List<GetTotal>>, t: Throwable) {
                            return t.printStackTrace()
                        }
                    })
            }

            override fun onFailure(call: Call<List<getCart>>, t: Throwable) {
                return t.printStackTrace()
            }
        })


        bindingCartFrag.confirmBtn.setOnClickListener {

            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.confirm_dialog_layout, null)
            val myBuilder = AlertDialog.Builder(context)
            myBuilder.setView(mDialogView)

            myBuilder.setPositiveButton("ยกเลิก") { dialog, which ->
                dialog.dismiss()
            }

            myBuilder.setNegativeButton("ยืนยัน") { dialog, which ->
                val serv: PreawpungAPI = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PreawpungAPI::class.java)
                serv.getCart(
                    getAccount_id.toString().toInt()
                ).enqueue(object : Callback<List<getCart>> {
                    override fun onResponse(
                        call: Call<List<getCart>>,
                        response: Response<List<getCart>>
                    ) {
                        response.body()?.forEach {
                            orderID.add(1, it.order_id.toString())
                        }
                        val sdf = SimpleDateFormat("yyyy/M/dd")
                        val stf = SimpleDateFormat("HH:mm:ss")
                        val currentDate = sdf.format(Date())
                        val currentTime = stf.format(Date())
                        val serv: PreawpungAPI = Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:3000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(PreawpungAPI::class.java)
                        serv.confirmOrder(
                            orderID[1].toString().toInt(),
                            currentDate.toString(),
                            currentTime.toString(),
                            ""
                        )
                            .enqueue(object : Callback<ConfirmOrder> {
                                override fun onResponse(
                                    call: Call<ConfirmOrder>,
                                    response: retrofit2.Response<ConfirmOrder>
                                ) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(
                                            context,
                                            "ยืนยันคำสั่งซื้อสำเร็จ",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()

                                        val apiCreate: PreawpungAPI = Retrofit.Builder()
                                            .baseUrl("http://10.0.2.2:3000/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build()
                                            .create(PreawpungAPI::class.java)
                                        apiCreate.createorder(
                                            "0.0".toString().toFloat(),
                                            0,
                                            getAccount_id.toString().toInt()
                                        ).enqueue(object : Callback<CreateOrder> {
                                            override fun onResponse(
                                                call: Call<CreateOrder>,
                                                response: retrofit2.Response<CreateOrder>
                                            ) {
                                                println(getAccount_id.toString())
                                                if (response.isSuccessful()) {
                                                    val i = Intent(
                                                        context,
                                                        OrderDetailActivity::class.java
                                                    )
                                                    i.putExtra("order_id", orderID[1].toString())
                                                    startActivity(i)
                                                    println("Create Order Successfully")
                                                } else {
                                                    println("Create Order Not Successfully")
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<CreateOrder>,
                                                t: Throwable
                                            ) {
                                                Toast.makeText(
                                                    context,
                                                    "Error onFailure Create Order" + t.message,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        })

                                    } else {
                                        Toast.makeText(
                                            context,
                                            "ยืนยันไม่สำเร็จ",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                                override fun onFailure(call: Call<ConfirmOrder>, t: Throwable) {
                                    return t.printStackTrace()
                                }
                            })
                    }
                    override fun onFailure(call: Call<List<getCart>>, t: Throwable) {
                        return t.printStackTrace()
                    }
                })
            }
            myBuilder.show()
        }

        //Swipe to refresh

        bindingCartFrag.swipe.setOnRefreshListener {
            var getAccount_id = arguments?.getString("account_id")
            var orderID = mutableListOf<String>()
            orderID.add(0, "0")


            val serv: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            serv.getCart(
                getAccount_id.toString().toInt()
            ).enqueue(object : Callback<List<getCart>> {
                override fun onResponse(
                    call: Call<List<getCart>>,
                    response: Response<List<getCart>>
                ) {
                    response.body()?.forEach {
                        orderID.add(0, it.order_id.toString())
                    }
                    cartList.clear()
                    val api: PreawpungAPI = Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PreawpungAPI::class.java)
                    api.cart(
                        orderID[0].toString()
                    )
                        .enqueue(object : Callback<List<Cart>> {
                            override fun onResponse(
                                call: Call<List<Cart>>,
                                response: Response<List<Cart>>
                            ) {
                                response.body()?.forEach {
                                    cartList.add(
                                        Cart(
                                            orderID[0].toInt(),
                                            it.product_id,
                                            it.product_name,
                                            it.product_pic,
                                            it.product_detail,
                                            it.product_price_tmm,
                                            it.product_amount,
                                            it.sum,
                                        )
                                    )
                                }
                                bindingCartFrag.basketRecycler.adapter =
                                    CartsAdapter(cartList, requireContext())
                                bindingCartFrag.basketRecycler.layoutManager =
                                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)

                            }

                            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                                return t.printStackTrace()
                            }
                        })


                    var total = mutableListOf<String>()
                    val api2: PreawpungAPI = Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PreawpungAPI::class.java)
                    api2.getTotal(
                        orderID[0].toString()
                    )
                        .enqueue(object : Callback<List<GetTotal>> {
                            override fun onResponse(
                                call: Call<List<GetTotal>>,
                                response: Response<List<GetTotal>>
                            ) {
                                response.body()?.forEach {
                                    total.add(0, it.total.toString())
                                }

                                bindingCartFrag.sumPrice.text = "รวมทั้งหมด ฿" + total[0]
                                if (total[0] == "0.0") {
                                    bindingCartFrag.confirmBtn?.isEnabled = false
                                    bindingCartFrag.confirmBtn?.setBackgroundColor(
                                        ContextCompat.getColor(
                                            requireContext(),
                                            R.color.grey
                                        )
                                    )
                                }
                            }

                            override fun onFailure(call: Call<List<GetTotal>>, t: Throwable) {
                                return t.printStackTrace()
                            }
                        })
                }

                override fun onFailure(call: Call<List<getCart>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })


            bindingCartFrag.confirmBtn.setOnClickListener {

                val mDialogView =
                    LayoutInflater.from(context).inflate(R.layout.confirm_dialog_layout, null)
                val myBuilder = AlertDialog.Builder(context)
                myBuilder.setView(mDialogView)

                myBuilder.setPositiveButton("ยกเลิก") { dialog, which ->
                    dialog.dismiss()
                }

                myBuilder.setNegativeButton("ยืนยัน") { dialog, which ->
                    val serv: PreawpungAPI = Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PreawpungAPI::class.java)
                    serv.getCart(
                        getAccount_id.toString().toInt()
                    ).enqueue(object : Callback<List<getCart>> {
                        override fun onResponse(
                            call: Call<List<getCart>>,
                            response: Response<List<getCart>>
                        ) {
                            response.body()?.forEach {
                                orderID.add(1, it.order_id.toString())
                            }
                            val sdf = SimpleDateFormat("yyyy/M/dd")
                            val stf = SimpleDateFormat("HH:mm:ss")
                            val currentDate = sdf.format(Date())
                            val currentTime = stf.format(Date())
                            val serv: PreawpungAPI = Retrofit.Builder()
                                .baseUrl("http://10.0.2.2:3000/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                                .create(PreawpungAPI::class.java)
                            serv.confirmOrder(
                                orderID[1].toString().toInt(),
                                currentDate.toString(),
                                currentTime.toString(),
                                ""
                            )
                                .enqueue(object : Callback<ConfirmOrder> {
                                    override fun onResponse(
                                        call: Call<ConfirmOrder>,
                                        response: retrofit2.Response<ConfirmOrder>
                                    ) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(
                                                context,
                                                "ยืนยันคำสั่งซื้อสำเร็จ",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                            val apiCreate: PreawpungAPI = Retrofit.Builder()
                                                .baseUrl("http://10.0.2.2:3000/")
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build()
                                                .create(PreawpungAPI::class.java)
                                            apiCreate.createorder(
                                                "0.0".toString().toFloat(),
                                                0,
                                                getAccount_id.toString().toInt()
                                            ).enqueue(object : Callback<CreateOrder> {
                                                override fun onResponse(
                                                    call: Call<CreateOrder>,
                                                    response: retrofit2.Response<CreateOrder>
                                                ) {
                                                    println(getAccount_id.toString())
                                                    if (response.isSuccessful()) {
                                                        val i = Intent(
                                                            context,
                                                            OrderDetailActivity::class.java
                                                        )
                                                        i.putExtra("order_id", orderID[1].toString())
                                                        startActivity(i)
                                                        println("Create Order Successfully")
                                                    } else {
                                                        println("Create Order Not Successfully")
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<CreateOrder>,
                                                    t: Throwable
                                                ) {
                                                    Toast.makeText(
                                                        context,
                                                        "Error onFailure Create Order" + t.message,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            })

                                        } else {
                                            Toast.makeText(
                                                context,
                                                "ยืนยันไม่สำเร็จ",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                    }
                                    override fun onFailure(call: Call<ConfirmOrder>, t: Throwable) {
                                        return t.printStackTrace()
                                    }
                                })
                        }
                        override fun onFailure(call: Call<List<getCart>>, t: Throwable) {
                            return t.printStackTrace()
                        }
                    })
                }
                myBuilder.show()
            }
            bindingCartFrag.swipe.isRefreshing = false
        }

        return bindingCartFrag.root
    }

}