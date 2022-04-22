package com.example.preawpung.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.preawpung.DataClass.Order
import com.example.preawpung.EmpCheckDetailActivity
import com.example.preawpung.EmpHisDetailActivity
import com.example.preawpung.R
import com.example.preawpung.databinding.HisEmpItemLayoutBinding

class EmpHisAdapter (var HisEmpList:ArrayList<Order>?, val context: Context):
    RecyclerView.Adapter<EmpHisAdapter.Viewholder>(){
    inner class Viewholder(view: View, val binding: HisEmpItemLayoutBinding):
        RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpHisAdapter.Viewholder {
        val binding = HisEmpItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: EmpHisAdapter.Viewholder, position: Int) {
        val binding = holder.binding

        var oStatus = ""

        if(HisEmpList!![position].order_status == 4){
            binding.orderStatus?.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.orange
                )
            )
            oStatus = "อยู่ระหว่างจัดส่ง"
        }

        if(HisEmpList!![position].order_status == 6){
            oStatus = "จัดส่งสำเร็จ"
        }


        Glide.with(context).load(HisEmpList!![position].slip_pic).into(binding.imgCheck)
        binding.orderId.text = "หมายเลขคำสั่งซื้อ : "+ HisEmpList!![position].order_id.toString()
        binding.orderDate.text = "วันที่สั่งซื้อ : "+HisEmpList!![position].order_date
        binding.orderStatus.text = oStatus

        binding.item.setOnClickListener {
            val i = Intent(context, EmpHisDetailActivity::class.java)
            i.putExtra("order_id",HisEmpList!![position].order_id.toString())
            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return HisEmpList!!.size
    }
}