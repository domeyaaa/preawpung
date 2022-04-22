package com.example.preawpung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.DataClass.Account
import com.example.preawpung.databinding.ActivityNewPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest

class NewPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
            startActivity(Intent(this,LoginActivity::class.java))
        }

        val email = intent.getStringExtra("account_email")

        binding.confirmBtn.setOnClickListener {
            val pw1 = binding.pw1.text.toString()
            val pw2 = binding.pw2.text.toString()

            if(pw1.isNullOrBlank() || pw2.isNullOrBlank()){
                Toast.makeText(applicationContext,"กรุณากรอกข้อมูลให้ครบถ้วน",Toast.LENGTH_LONG).show()
            }else{
                if(pw1 != pw2){
                    Toast.makeText(applicationContext,"รหัสผ่านไม่ตรงกัน",Toast.LENGTH_SHORT).show()
                }else{
                    val pw_md5 = md5Hash(pw1)
                    val api: PreawpungAPI = Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PreawpungAPI::class.java)
                    api.newPassword(
                        email.toString(),
                        pw_md5,
                    ).enqueue(object : Callback<Account> {
                        override fun onResponse(call: Call<Account>, response: Response<Account>) {
                            if(response.isSuccessful){
                                Toast.makeText(applicationContext,"ตั้งรหัสผ่านสำเร็จ",Toast.LENGTH_SHORT).show()
                                finish()
                                startActivity(Intent(applicationContext,LoginActivity::class.java))
                            }else{
                                Toast.makeText(applicationContext,"ตั้งรหัสผ่านไม่สำเร็จสำเร็จ",Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Account>, t: Throwable) {
                            Toast.makeText(applicationContext,"เกิดข้อผิดพลาด",Toast.LENGTH_SHORT).show()
                        }

                    })

                    finish()
                    startActivity(Intent(this,LoginActivity::class.java))
                }
            }
        }

    }

    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }
}