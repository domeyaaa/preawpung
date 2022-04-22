package com.example.preawpung.Adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.CartProductDetailActivity
import com.example.preawpung.DataClass.*
import com.example.preawpung.R
import com.example.preawpung.databinding.CartItemLayoutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CartsAdapter(var cartList: ArrayList<Cart>?, val context: Context) :
    RecyclerView.Adapter<CartsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View, val binding: CartItemLayoutBinding) :
        RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartsAdapter.ViewHolder {
        val binding =
            CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(
        holder: CartsAdapter.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val binding = holder.binding



        binding.itemCartCard.setOnClickListener {
            val i = Intent(context, CartProductDetailActivity::class.java)
            i.putExtra("product_nameCart", cartList!![position].product_name)
            i.putExtra("product_priceCart", cartList!![position].product_price_tmm.toString())
            i.putExtra("product_amountCart", cartList!![position].product_amount.toString())
            i.putExtra("product_picCart", cartList!![position].product_pic)
            i.putExtra("product_detailCart", cartList!![position].product_detail)
            context.startActivity(i)
        }

        Glide.with(context).load(cartList!![position].product_pic).into(binding.itemImg1)
        binding.itemName.text = cartList!![position].product_name
        binding.itemDetail.text = cartList!![position].product_detail
        binding.itemPrice.text = cartList!![position].sum.toString()
        binding.itemAmount.text = cartList!![position].product_amount.toString()

        if (cartList!![position].product_amount.toString().toInt() == 1) {
            binding.btnMinus.isEnabled = false
            binding.btnMinus?.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.grey
                )
            )
        }

        binding.btnPlus.setOnClickListener {
            val serv: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            serv.addAmount(
                cartList!![position].order_id,
                cartList!![position].product_id,
                ""
            )
                .enqueue(object : Callback<Amount> {
                    @SuppressLint("ResourceType")
                    override fun onResponse(
                        call: Call<Amount>,
                        response: retrofit2.Response<Amount>
                    ) {
                        if (response.isSuccessful()) {
                            val x = binding.itemAmount.text.toString()
                            var y = x.toString().toInt()
                            y = y + 1
                            if (y > 1) {
                                binding.btnMinus.isEnabled = true
                                binding.btnMinus?.setBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.teal_200
                                    )
                                )
                            }
                            //edit price
                            val p = binding.itemPrice.text.toString()
                            var p1 = p.toString().toFloat()
                            p1 += cartList!![position].product_price_tmm.toFloat()
                            binding.itemPrice.text = p1.toString()
                            binding.itemAmount.text = y.toString()
                            Toast.makeText(context, "เพิ่มจำนวนสำเร็จ", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "เพิ่มจำนวนไม่สำเร็จ", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<Amount>, t: Throwable) {
                        return t.printStackTrace()
                    }
                })
        }

        binding.delete.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.del_dialog_layout, null)
            val myBuilder = AlertDialog.Builder(context)
            myBuilder.setView(mDialogView)

            myBuilder.setPositiveButton("ยกเลิก") { dialog, which ->
                dialog.dismiss()
            }

            myBuilder.setNegativeButton("ยืนยัน") { dialog, which ->
                dialog.dismiss()
            }
            myBuilder.show()
        }


        binding.btnMinus.setOnClickListener {
            val serv: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            serv.removeAmount(
                cartList!![position].order_id,
                cartList!![position].product_id,
                ""
            )
                .enqueue(object : Callback<Amount> {
                    override fun onResponse(
                        call: Call<Amount>,
                        response: retrofit2.Response<Amount>
                    ) {
                        if (response.isSuccessful()) {
                            val x = binding.itemAmount.text.toString()
                            var y = x.toString().toInt()
                            y = y - 1
                            if (y == 1) {
                                binding.btnMinus.isEnabled = false
                                binding.btnMinus?.setBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.grey
                                    )
                                )
                            }
                            //edit price
                            val p = binding.itemPrice.text.toString()
                            var p1 = p.toString().toFloat()
                            p1 -= cartList!![position].product_price_tmm.toFloat()
                            binding.itemPrice.text = p1.toString()


                            binding.itemAmount.text = y.toString()
                            Toast.makeText(context, "ลดจำนวนสำเร็จ", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "ลดจำนวนไม่สำเร็จ", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Amount>, t: Throwable) {
                        return t.printStackTrace()
                    }
                })
        }

        binding.delete.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.del_dialog_layout, null)
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
                serv.delProduct(
                    cartList!![position].order_id,
                    cartList!![position].product_id,
                ).enqueue(object : Callback<DeleteProduct> {
                    override fun onResponse(
                        call: Call<DeleteProduct>,
                        response: Response<DeleteProduct>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "ลบสำเร็จ", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "ลบไม่สำเร็จ", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<DeleteProduct>, t: Throwable) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
                    }
                })
            }
            myBuilder.show()
        }
    }

    override fun getItemCount(): Int {
        return cartList!!.size
    }
}