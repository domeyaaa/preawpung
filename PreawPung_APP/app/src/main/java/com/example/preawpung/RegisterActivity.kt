package com.example.preawpung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.DataClass.Account
import com.example.preawpung.Fragments.DatePickerFragment
import com.example.preawpung.databinding.ActivityRegisterBinding
import okio.HashingSink.md5
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {

    private lateinit var regBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        regBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(regBinding.root)

        regBinding.backBtn.setOnClickListener() {
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    fun showDatePickerDialog(v: View) {
        val newDateFragment = DatePickerFragment()
        newDateFragment.show(supportFragmentManager, "Select Date")
    }

    fun register(v: View) {

        val username = regBinding.usernameREG.text
        val name = regBinding.nameREG.text
        val email = regBinding.emailREG.text
        val pw1 = regBinding.password1REG.text
        val pw2 = regBinding.password2REG.text
        val birthday = regBinding.birthdayREG.text


        var gender_str = ""
        if (regBinding.male.isChecked) {
            gender_str = "ชาย"
        }

        if (regBinding.female.isChecked) {
            gender_str = "หญิง"
        }

        if (username.isNullOrBlank() || name.isNullOrBlank() || email.isNullOrBlank() || pw1.isNullOrBlank() || pw2.isNullOrBlank() || birthday == "ปปปป-ดด-วว" || gender_str == ""){
            return Toast.makeText(applicationContext,"กรุณากรอกข้อมูลให้ครบถ้วน",Toast.LENGTH_SHORT).show()
        }

        var role = "customer"

        var pwd1 = regBinding.password1REG.text.toString()
        var pwd2 = regBinding.password2REG.text.toString()

        if (pwd1 == pwd2) {

            var password_md5 = md5Hash(pwd1)

            val api: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            api.registerAcc(
                regBinding.usernameREG.text.toString(),
                password_md5,
                regBinding.nameREG.text.toString(),
                gender_str,
                regBinding.emailREG.text.toString(),
                regBinding.birthdayREG.text.toString(),
                role,
            ).enqueue(object : Callback<Account> {
                override fun onResponse(
                    call: Call<Account>,
                    response: retrofit2.Response<Account>
                ) {
                    if (response.isSuccessful()) {
                        if(response.body()?.error == true){
                            Toast.makeText(
                                applicationContext,
                                "สมัครสมาชิกไม่สำเร็จ ชื่อผู้ใช้ หรือ อีเมล อาจซ้ำ",
                                Toast.LENGTH_LONG
                            ).show()
                        }else {
                            Toast.makeText(
                                applicationContext,
                                "สมัครสมาชิกสำเร็จ",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val i = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "สมัครสมาชิกไม่สำเร็จ ชื่อผู้ใช้ หรือ อีเมล อาจซ้ำ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Error onFailure" + t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        } else {
            Toast.makeText(
                applicationContext,
                "รหัสผ่านไม่ตรงกัน",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }
}