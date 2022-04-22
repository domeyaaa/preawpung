package com.example.preawpung.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.preawpung.AddProductActivity
import com.example.preawpung.DataClass.Product
import com.example.preawpung.databinding.SearchItemLayoutBinding


class SearchAdapter(var productList: ArrayList<Product>?,val context: Context):
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder (view: View,val binding: SearchItemLayoutBinding):RecyclerView.ViewHolder(view){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val binding = SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val binding = holder.binding

        binding.tvSearch.text = productList!![position].product_name

        binding.tvSearch.setOnClickListener(){
            val i = Intent(context, AddProductActivity::class.java)
            i.putExtra("account_id",productList!![position].account_id)
            i.putExtra("product_id",productList!![position].product_id.toString())
            i.putExtra("product_name",productList!![position].product_name)
            i.putExtra("product_pic",productList!![position].product_pic)
            i.putExtra("product_price",productList!![position].product_price.toString())
            i.putExtra("product_stock_amount",productList!![position].product_stock_amount.toString())
            i.putExtra("product_detail",productList!![position].product_detail)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return productList!!.size
    }

}