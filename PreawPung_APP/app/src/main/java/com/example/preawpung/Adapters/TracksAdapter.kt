package com.example.preawpung.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.preawpung.DataClass.Check
import com.example.preawpung.DataClass.Track
import com.example.preawpung.UpdateTrackingActivity
import com.example.preawpung.databinding.CheckItemLauoutBinding

class TracksAdapter(var trackList:ArrayList<Track>?, val context: Context):
    RecyclerView.Adapter<TracksAdapter.Viewholder>(){
    inner class Viewholder(view: View, val binding: CheckItemLauoutBinding):
        RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksAdapter.Viewholder {
        val binding = CheckItemLauoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: TracksAdapter.Viewholder, position: Int) {
        val binding = holder.binding

        binding.checkCard.setOnClickListener{
            val i = Intent(context,UpdateTrackingActivity::class.java)
            i.putExtra("order_id",trackList!![position].order_id.toString())
            i.putExtra("slip_pic",trackList!![position].slip_pic)
            i.putExtra("order_date",trackList!![position].order_date)
            context.startActivity(i)
        }
        Glide.with(context).load(trackList!![position].slip_pic).into(binding.imgCheck)
        binding.orderId.text = "หมายเลขคำสั่งซื้อ : "+ trackList!![position].order_id.toString()
        binding.orderDate.text = "วันที่สั่งซื้อ : "+trackList!![position].order_date
    }

    override fun getItemCount(): Int {
        return trackList!!.size
    }
}