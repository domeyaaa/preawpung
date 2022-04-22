package com.example.preawpung.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.preawpung.DataClass.Check
import com.example.preawpung.DataClass.CheckDetail
import com.example.preawpung.EmpCheckDetailActivity
import com.example.preawpung.databinding.CheckItemLauoutBinding
import com.example.preawpung.databinding.ItemProductDetailLayoutBinding

class CheckDetailsAdapter(var checkDetailList:ArrayList<CheckDetail>?, val context: Context):
    RecyclerView.Adapter<CheckDetailsAdapter.Viewholder>(){
    inner class Viewholder(view: View, val binding:ItemProductDetailLayoutBinding):
        RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckDetailsAdapter.Viewholder {
        val binding = ItemProductDetailLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: CheckDetailsAdapter.Viewholder, position: Int) {
        val binding = holder.binding

        binding.itemProductDetailName.text = checkDetailList!![position].product_name
        Glide.with(context).load(checkDetailList!![position].product_pic).into(binding.itemProductDetailImg)
        binding.itemProductDetailAmount.text = checkDetailList!![position].product_amount.toString()

    }

    override fun getItemCount(): Int {
        return checkDetailList!!.size
    }
}