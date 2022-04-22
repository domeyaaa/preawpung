package com.example.preawpung

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.DataClass.Login
import com.example.preawpung.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding


    var userList = mutableListOf<Any>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.regBtn.setOnClickListener{
            var i = Intent(this,RegisterActivity::class.java)
            startActivity(i)
        }
        loginBinding.forgotPassword.setOnClickListener {
            startActivity(Intent(this,SendEmailActivity::class.java))
        }
    }


    fun login(v: View) {
        userList.clear()
        userList.add(0,"")
        userList.add(1,"")
        var account_username = loginBinding.usernameEDT.text.toString()
        var account_password_input = loginBinding.passwordEDT.text.toString()
        var pwdMd5 = md5Hash(account_password_input)

        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.login(
            account_username,
            pwdMd5
        ).enqueue(object : Callback<List<Login>> {
            override fun onResponse(
                call: Call<List<Login>>,
                response: Response<List<Login>>
            ) {
                response.body()?.forEach {
                    userList.add(0,it.account_role)
                    userList.add(1,it.account_id)
                }
                if (userList[0] == "customer") {
                    Toast.makeText(applicationContext, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show()
                    var i = Intent(this@LoginActivity, HomeActivity::class.java)
                    i.putExtra("account_id",userList[1].toString())
                    startActivity(i)
                } else if (userList[0] == "employee") {
                    Toast.makeText(applicationContext, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT)
                        .show()
                    var i = Intent(this@LoginActivity, EmpHomeActivity::class.java)
                    i.putExtra("account_id",userList[1].toString())
                    startActivity(i)
                } else {
                    Toast.makeText(applicationContext, "ชื่อผู้ใช้ หรือ รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Login>>, t: Throwable) {
                return t.printStackTrace()
            }

        })
    }


    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }
}