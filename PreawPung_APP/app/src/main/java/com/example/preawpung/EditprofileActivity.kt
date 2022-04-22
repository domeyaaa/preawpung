package com.example.preawpung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.DataClass.Account
import com.example.preawpung.Fragments.DatePickerFragment
import com.example.preawpung.Fragments.DatePickerFragmentProfile
import com.example.preawpung.databinding.ActivityEditprofileBinding
import com.example.preawpung.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditprofileActivity : AppCompatActivity() {
    private lateinit var bindingEdit: ActivityEditprofileBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        bindingEdit = ActivityEditprofileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindingEdit.root)

        val account_id = intent.getStringExtra("account_id")
        val name = intent.getStringExtra("account_name")
        val email = intent.getStringExtra("account_email")
        val birthday = intent.getStringExtra("account_birthday")
        val gender = intent.getStringExtra("account_gender")
        val tel = intent.getStringExtra("account_tel")
        val address = intent.getStringExtra("account_address")

        if (gender.toString() == "ชาย") {
            bindingEdit.male.isChecked = true
        }
        if (gender.toString() == "หญิง") {
            bindingEdit.female.isChecked = true
        }

        bindingEdit.nameProfileEDT.setText(name.toString())
        bindingEdit.emailProfileEDT.setText(email.toString())
        bindingEdit.birthdayProfileEDT.text = birthday
        bindingEdit.telProfileEDT.setText(tel)
        bindingEdit.addressProfileEDT.setText(address)

        bindingEdit.backBtn.setOnClickListener() {
            finish()
        }

        bindingEdit.saveEditBtn.setOnClickListener() {
            Toast.makeText(applicationContext, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show()
            finish()
        }

        bindingEdit.saveEditBtn.setOnClickListener {

            var gender_str = ""
            if (bindingEdit.male.isChecked) {
                gender_str = "ชาย"
            }
            if (bindingEdit.female.isChecked) {
                gender_str = "หญิง"
            }

            val serv: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            serv.updateProfileCus(
                account_id.toString().toInt(),
                bindingEdit.nameProfileEDT.text.toString(),
                bindingEdit.emailProfileEDT.text.toString(),
                bindingEdit.birthdayProfileEDT.text.toString(),
                gender_str,
                bindingEdit.telProfileEDT.text.toString(),
                bindingEdit.addressProfileEDT.text.toString()
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

    fun showDate(view: View) {
        val newDateFragment = DatePickerFragmentProfile()
        newDateFragment.show(supportFragmentManager, "Select Date")
    }
}