package com.example.preawpung

import android.R.attr
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.DataClass.Slip
import com.example.preawpung.databinding.ActivitySendSlipBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import android.R.attr.label

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


class SendSlipActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendSlipBinding
    lateinit var imageUri : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySendSlipBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val account_id = intent.getStringExtra("account_id")
        val order_id = intent.getStringExtra("order_id")
        val order_total = intent.getStringExtra("order_total")
        val bankArray = resources.getStringArray(R.array.bankName_array)
        val arrayAdapter = ArrayAdapter(this, R.layout.color_spinner, bankArray)
        arrayAdapter.setDropDownViewResource(R.layout.dropdown_spinner)

        binding.sumPrice.text = "฿"+order_total.toString()


        binding.chooseBank.adapter = arrayAdapter

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.chooseBank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.copy.setOnClickListener {
            var clipBoard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val bankNum = binding.bankNum.text.toString()
            val clip = ClipData.newPlainText("Copied Text", bankNum)
            clipBoard.setPrimaryClip(clip)
            Toast.makeText(applicationContext,"คัดลอกแล้ว",Toast.LENGTH_SHORT).show()
        }

        binding.uploadPic.setOnClickListener {
            selectImg()
        }
        binding.btnSubmit.setOnClickListener {
            uploadImg(order_id.toString(),account_id.toString())
        }
    }

    private fun uploadImg(order_id:String,account_id:String) {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("กำลังอัปโหลดไฟล์...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)
        val fn = order_id+"_$filename"
        val storageReference = FirebaseStorage.getInstance().getReference().child("images/$fn")

        storageReference.putFile(imageUri).addOnSuccessListener {

            binding.uploadPic.setImageURI(null)
            if (progressDialog.isShowing) progressDialog.dismiss()

            val apiCreate: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            apiCreate.addSlip(
                order_id.toInt(),
                binding.chooseBank.selectedItem.toString(),
                binding.edtLast4num.text.toString().toInt(),
                "https://firebasestorage.googleapis.com/v0/b/preawpung-14128.appspot.com/o/images%2F$fn?alt=media",
                binding.edtTransferPrice.text.toString().toFloat(),
                binding.date.text.toString(),
                binding.time.text.toString()
            ).enqueue(object : Callback<Slip>{
                override fun onResponse(call: Call<Slip>, response: Response<Slip>) {
                    if(response.isSuccessful()){
                        Toast.makeText(applicationContext, "อัปโหลดสำเร็จ", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(applicationContext, "อัปข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<Slip>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error onFailure", Toast.LENGTH_SHORT).show()
                }

            })

        }.addOnFailureListener {

            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(applicationContext, "เกิดข้อผิดพลาดในการอัปโหลด", Toast.LENGTH_SHORT).show()

        }
    }

    private fun selectImg() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {

            imageUri = data?.data!!
            binding.uploadPic.setImageURI(imageUri)

        }
    }


    fun showDatePicker(view: View) {
        val newDateFragment = DatePickerFragment()
        newDateFragment.show(supportFragmentManager, "Date Picker")
    }

    fun showTimePicker(view: View) {
        val newTimeFragment = TimePickerFragment()
        newTimeFragment.show(supportFragmentManager, "Time Picker")
    }
}