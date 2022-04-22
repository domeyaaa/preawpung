package com.example.preawpung.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.preawpung.DataClass.Check
import com.example.preawpung.EmpCheckDetailActivity
import com.example.preawpung.databinding.CheckItemLauoutBinding

class ChecksAdapter(var checkList:ArrayList<Check>?, val context: Context):
RecyclerView.Adapter<ChecksAdapter.Viewholder>(){
    inner class Viewholder(view: View, val binding:CheckItemLauoutBinding):
    RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecksAdapter.Viewholder {
        val binding = CheckItemLauoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: ChecksAdapter.Viewholder, position: Int) {
        val binding = holder.binding

        binding.checkCard.setOnClickListener{
            val i = Intent(context,EmpCheckDetailActivity::class.java)
            i.putExtra("order_id",checkList!![position].order_id.toString())
            context.startActivity(i)
        }

        Glide.with(context).load(checkList!![position].slip_pic).into(binding.imgCheck)
        binding.orderId.text = "หมายเลขคำสั่งซื้อ : "+ checkList!![position].order_id.toString()
        binding.orderDate.text = "วันที่สั่งซื้อ : "+checkList!![position].order_date
    }



    override fun getItemCount(): Int {
        return checkList!!.size
    }
}