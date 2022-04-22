package com.example.preawpung.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.preawpung.DataClass.ProductDetail
import com.example.preawpung.databinding.ItemProductDetailLayoutBinding

class ProductDetailAdapter(var productDetailList: ArrayList<ProductDetail>?,val context: Context):
    RecyclerView.Adapter<ProductDetailAdapter.ViewHolder>(){

    inner class  ViewHolder(view:View,val binding: ItemProductDetailLayoutBinding):
    RecyclerView.ViewHolder(view){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailAdapter.ViewHolder {
        val binding = ItemProductDetailLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ProductDetailAdapter.ViewHolder, position: Int) {
        val binding = holder.binding

        Glide.with(context).load(productDetailList!![position].product_pic).into(binding.itemProductDetailImg)
        binding.itemProductDetailName.text = productDetailList!![position].product_name
        binding.itemProductDetailAmount.text = "X " + productDetailList!![position].product_amount.toString()
        binding.itemProductDetailPrice.text = "฿"+productDetailList!![position].product_price_tmm.toString()
    }

    override fun getItemCount(): Int {
        return productDetailList!!.size
    }

}