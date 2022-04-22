package com.example.preawpung

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.TimeUtils
import android.widget.TextView
import android.widget.Toast
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.databinding.ActivityOtpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private var tvTimer: TextView? = null
    private var isStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOtpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        startTimer()

        binding.backBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        val email = intent.getStringExtra("account_email")
        binding.email.text = email

        val otp = intent.getStringExtra("otp")

        binding.confirmBtn.setOnClickListener {

            if (binding.otp.text.isNullOrBlank()) {
                Toast.makeText(applicationContext, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG)
                    .show()
            } else {
                val otp_input = binding.otp.text
                val en_otp = md5Hash(otp_input.toString())
                if (en_otp == otp) {
                    Toast.makeText(applicationContext, "ยืนยันตัวตนสำเร็จ", Toast.LENGTH_SHORT)
                        .show()
                    stopTimer()
                    val i = Intent(this, NewPasswordActivity::class.java)
                    i.putExtra("account_email", email)
                    finish()
                    startActivity(i)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "รหัสยืนยันตัวตนไม่ถูกต้อง",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun initViews() {
        tvTimer = findViewById(R.id.time)
    }

    private var countDownTimer = object : CountDownTimer(1000 * 60 * 2,1000){
        override fun onTick(millisUntilFinished: Long) {
            Log.d(TAG,"onTick: ${millisUntilFinished/1000f}")
            tvTimer?.text = getString(R.string.formatted_time,
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60,
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60)
        }

        override fun onFinish() {
            Log.d(TAG,"onFinish: called")
            startActivity(Intent(applicationContext,LoginActivity::class.java))
        }

    }

    private fun startTimer(){
        countDownTimer.start()
        isStarted = true
    }

    private fun stopTimer(){
        countDownTimer.cancel()
        isStarted = false
    }

    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }
}