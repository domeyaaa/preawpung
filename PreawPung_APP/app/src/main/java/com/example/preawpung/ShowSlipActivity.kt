package com.example.preawpung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.preawpung.databinding.ActivityShowSlipBinding

class ShowSlipActivity : AppCompatActivity() {
    private lateinit var binding:ActivityShowSlipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowSlipBinding.inflate(layoutInflater)
        val slip_pic = intent.getStringExtra("slip_pic")

        Glide.with(applicationContext).load(slip_pic).into(binding.slipPic)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            finish()
        }
    }
}