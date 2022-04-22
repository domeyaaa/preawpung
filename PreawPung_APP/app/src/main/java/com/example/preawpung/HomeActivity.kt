package com.example.preawpung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.DataClass.Account
import com.example.preawpung.DataClass.CreateOrder
import com.example.preawpung.DataClass.getCart
import com.example.preawpung.Fragments.*
import com.example.preawpung.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        val account_id = intent.getStringExtra("account_id")

        setContentView(homeBinding.root)

        val homeFragment = HomeFragment()
        val cartFragment = CartFragment()
        val historyFragment = HistoryFragment()
        val profileFragment = ProfileFragment()

        val bundle = Bundle()
        bundle.putString("account_id", account_id)

        val makeCurrentFragment = makeCurrentFragment(homeFragment)
        homeFragment.arguments = bundle

        homeBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    homeFragment.arguments = bundle
                    makeCurrentFragment(homeFragment)
                }
                R.id.ic_cart -> {
                    cartFragment.arguments = bundle
                    makeCurrentFragment(cartFragment)
                }
                R.id.ic_history -> {
                    historyFragment.arguments = bundle
                    makeCurrentFragment(historyFragment)
                }
                R.id.ic_profile -> {
                    profileFragment.arguments = bundle
                    makeCurrentFragment(profileFragment)
                }
            }
            true
        }

//        var badge = homeBinding.bottomNavigation.getOrCreateBadge(R.id.ic_cart)
//        badge.isVisible = true
//        var count_item_product: Int = 9
//        if (count_item_product > 0) {
//            badge.number = count_item_product
//        } else {
//            badge.isVisible = false
//        }

        var orderID = mutableListOf<String>()
        orderID.add(0,"")
        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.getCart(
            account_id.toString().toInt()
        ).enqueue(object : Callback<List<getCart>> {
            override fun onResponse(
                calrel: Call<List<getCart>>,
                response: Response<List<getCart>>
            ) {
                response.body()?.forEach {
                    orderID.add(0, it.order_id.toString())
                }
                if (orderID[0] == "") {
                    val api: PreawpungAPI = Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(PreawpungAPI::class.java)
                    api.createorder(
                        "0.0".toString().toFloat(),
                        0,
                        account_id.toString().toInt()
                    ).enqueue(object : Callback<CreateOrder> {
                        override fun onResponse(
                            call: Call<CreateOrder>,
                            response: retrofit2.Response<CreateOrder>
                        ) {
                            if (response.isSuccessful()) {
                                println("Create Order Successfully")
                            } else {
                                println("Create Order Not Successfully")
                            }
                        }

                        override fun onFailure(call: Call<CreateOrder>, t: Throwable) {
                            Toast.makeText(
                                applicationContext,
                                "Error onFailure" + t.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<getCart>>, t: Throwable) {
                return t.printStackTrace()
            }
        })

    }

    fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}