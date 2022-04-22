package com.example.preawpung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.preawpung.databinding.ActivityLoginBinding

class MainActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.regBtn.setOnClickListener() {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginBinding.loginBtn.setOnClickListener() {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)


        }

        //ไม่ใช่หน้านี้
    }
}
