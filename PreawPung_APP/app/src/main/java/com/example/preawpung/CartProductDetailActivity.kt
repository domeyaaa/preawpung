package com.example.preawpung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.preawpung.databinding.ActivityCartProductDetailBinding

class CartProductDetailActivity : AppCompatActivity() {
    private lateinit var binding :ActivityCartProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product_name_cart = intent.getStringExtra("product_nameCart")
        val product_pic_cart = intent.getStringExtra("product_picCart")
        val product_detail_cart = intent.getStringExtra("product_detailCart")
        val product_price_cart = intent.getStringExtra("product_priceCart")

        binding.productName.text = product_name_cart
        binding.productPlus.text = product_detail_cart
        binding.productPrice.text = "฿"+ product_price_cart.toString()
        Glide.with(applicationContext).load(product_pic_cart).into(binding.imgProduct)

        binding.backBtn.setOnClickListener{
            finish()
        }
    }
}