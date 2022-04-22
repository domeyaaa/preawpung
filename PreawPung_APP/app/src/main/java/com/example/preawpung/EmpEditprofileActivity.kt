package com.example.preawpung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.DataClass.Account
import com.example.preawpung.Fragments.DatePickerFragmentProfile
import com.example.preawpung.databinding.ActivityEmpEditprofileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmpEditprofileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmpEditprofileBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityEmpEditprofileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            finish()
        }

        val account_id = intent.getStringExtra("account_id")
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val birthday = intent.getStringExtra("birthday")
        val gender = intent.getStringExtra("gender")

        if (gender == "ชาย"){
            binding.male.isChecked = true
        }
        if (gender == "หญิง"){
            binding.female.isChecked = true
        }

        binding.nameProfileEDT.setText(name)
        binding.emailProfileEDT.setText(email)
        binding.birthdayProfileEDT.text = birthday

        binding.saveEditBtn.setOnClickListener {

            var gender_str = ""
            if (binding.male.isChecked) {
                gender_str = "ชาย"
            }
            if (binding.female.isChecked) {
                gender_str = "หญิง"
            }

            val serv: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            serv.updateProfileEmp(
                account_id.toString().toInt(),
                binding.nameProfileEDT.text.toString(),
                binding.emailProfileEDT.text.toString(),
                binding.birthdayProfileEDT.text.toString(),
                gender_str,
            ).enqueue(object : Callback<Account> {
                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "บันทึกสำเร็จ", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "บันทึกไม่สำเร็จ", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Account>, t: Throwable) {
                    Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun showDate(view: View){
        val newDateFragment = DatePickerFragmentProfile()
        newDateFragment.show(supportFragmentManager,"Select Date")
    }
}