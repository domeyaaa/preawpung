package com.example.preawpung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.DataClass.Account
import com.example.preawpung.DataClass.OTP
import com.example.preawpung.databinding.ActivitySendEmailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SendEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySendEmailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    fun sendOtp(v: View){

        if(binding.email.text.isNullOrBlank()){
            return Toast.makeText(applicationContext,"กรุณากรอกข้อมูลให้ครบถ้วน",Toast.LENGTH_LONG).show()
        }

        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.sendEmail(
            binding.email.text.toString()
        ).enqueue(object:Callback<OTP>{
            override fun onResponse(call: Call<OTP>, response: Response<OTP>) {
                if (response.isSuccessful){
                    if(response.body()?.error == true){
                        Toast.makeText(applicationContext,"ไม่พบ E-mail ดังกล่าว !",Toast.LENGTH_SHORT).show()
                    }else{
                        val i = Intent(applicationContext,OtpActivity::class.java)
                        i.putExtra("account_email",binding.email.text.toString())
                        i.putExtra("otp",response.body()?.otp.toString())
                        finish()
                        startActivity(i)
                    }
                }else{
                    Toast.makeText(applicationContext,"เกิดข้อผิดพลาด",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<OTP>, t: Throwable) {
                Toast.makeText(applicationContext,"ไม่พบ E-mail ดังกล่าว ",Toast.LENGTH_SHORT).show()
            }
        })
    }
}