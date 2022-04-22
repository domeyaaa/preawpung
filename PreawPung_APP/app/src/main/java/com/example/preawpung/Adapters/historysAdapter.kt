package com.example.preawpung.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.preawpung.AddProductActivity
import com.example.preawpung.DataClass.History
import com.example.preawpung.DataClass.Product
import com.example.preawpung.OrderDetailActivity
import com.example.preawpung.R
import com.example.preawpung.databinding.HistoryItemLayoutBinding


class historysAdapter(var historyList: ArrayList<History>?, val context: Context) :
    RecyclerView.Adapter<historysAdapter.ViewHolder>() {

    inner class ViewHolder(view: View, val binding: HistoryItemLayoutBinding) :
        RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): historysAdapter.ViewHolder {
        val binding =
            HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: historysAdapter.ViewHolder, position: Int) {
        val binding = holder.binding
        if (historyList!![position].order_status == 1) {
            binding.orderStatusText.text = "ยังไม่ชำระเงิน"
            binding.orderStatusText.setTextColor(Color.parseColor("#C24229"))
        }

        if (historyList!![position].order_status == 2) {
            binding.orderStatusText.text = "รอตรวจสอบ"
            binding.orderStatusText.setTextColor(Color.parseColor("#ffcc79"))
        }

        if (historyList!![position].order_status == 3) {
            binding.orderStatusText.text = "รอจัดส่ง"
            binding.orderStatusText.setTextColor(Color.parseColor("#0097FF"))
        }

        if (historyList!![position].order_status == 4) {
            binding.orderStatusText.text = "อยู่ระหว่างจัดส่ง"
            binding.orderStatusText.setTextColor(Color.parseColor("#FFAE66"))
        }

        if (historyList!![position].order_status == 6) {
            binding.orderStatusText.text = "จัดส่งสำเร็จ"
            binding.orderStatusText.setTextColor(Color.parseColor("#1EDB28"))
        }

        binding.itemCard.setOnClickListener() {
            val i = Intent(context, OrderDetailActivity::class.java)
            i.putExtra("account_id", historyList!![position].account_id.toString())
            i.putExtra("order_id", historyList!![position].order_id.toString())
            i.putExtra("order_status", historyList!![position].order_status.toString())
            context.startActivity(i)
        }

        binding.orderNumText.text =
            "หมายเลขคำสั่งซื้อ : " + historyList!![position].order_id.toString()
        binding.orderDateHisText.text = "วันที่สั่งซื้อ : " + historyList!![position].order_date
        binding.orderTimeText.text =
            "เวลาที่สั่งซื้อ : " + historyList!![position].order_time + " น."
        binding.orderTotal.text = "฿"+historyList!![position].order_total.toString()
    }

    override fun getItemCount(): Int {
        return historyList!!.size
    }
}