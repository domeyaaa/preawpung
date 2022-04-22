package com.example.preawpung.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.CartsAdapter
import com.example.preawpung.Adapters.ProductsAdapter
import com.example.preawpung.DataClass.*
import com.example.preawpung.EditprofileActivity
import com.example.preawpung.LoginActivity
import com.example.preawpung.R
import com.example.preawpung.databinding.FragmentHomeBinding
import com.example.preawpung.databinding.FragmentProfileBinding
import org.json.JSONObject.NULL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {

    private lateinit var bindingProfile: FragmentProfileBinding
    var profile_arr = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingProfile = FragmentProfileBinding.inflate(layoutInflater)
        var getAccount_id = arguments?.getString("account_id")

        bindingProfile.editProfile.setOnClickListener() {
            val i = Intent(activity, EditprofileActivity::class.java)
            i.putExtra("account_id", getAccount_id)
            i.putExtra("account_name", bindingProfile.usernameProfile.text)
            i.putExtra("account_email", bindingProfile.emailProfile.text)
            i.putExtra("account_birthday", bindingProfile.birthdayProfile.text)
            i.putExtra("account_gender", bindingProfile.genderProfile.text)
            i.putExtra("account_tel", bindingProfile.telProfile.text)
            i.putExtra("account_address", bindingProfile.addressProfile.text)
            startActivity(i)
        }

        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.getProfile(
            getAccount_id.toString().toInt()
        ).enqueue(object : Callback<List<Account>> {
            override fun onResponse(call: Call<List<Account>>, response: Response<List<Account>>) {
                response.body()?.forEach {
                    profile_arr.add(0, it.account_name)
                    profile_arr.add(1, it.account_email)
                    profile_arr.add(2, it.account_birthday)
                    profile_arr.add(3, it.account_gender)
                    if (it.account_tel.isNullOrBlank()) {
                        profile_arr.add(4, "")
                    } else {
                        profile_arr.add(4, it.account_tel)
                    }
                    if (it.account_address.isNullOrBlank()) {
                        profile_arr.add(5, "")
                    } else {
                        profile_arr.add(5, it.account_address)
                    }
                }
                bindingProfile.usernameProfile.text = profile_arr[0].toString()
                bindingProfile.emailProfile.text = profile_arr[1].toString()
                bindingProfile.birthdayProfile.text = profile_arr[2].toString()
                bindingProfile.genderProfile.text = profile_arr[3].toString()
                bindingProfile.telProfile.text = profile_arr[4].toString()
                bindingProfile.addressProfile.text = profile_arr[5].toString()
            }

            override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                return t.printStackTrace()
            }
        })

        bindingProfile.logout.setOnClickListener {

            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.logout_dialog_layout, null)
            val myBuilder = AlertDialog.Builder(context)
            myBuilder.setView(mDialogView)

            myBuilder.setPositiveButton("ยกเลิก") { dialog, which ->
                dialog.dismiss()
            }

            myBuilder.setNegativeButton("ออกจากระบบ") { dialog, which ->
                val i = Intent(context, LoginActivity::class.java)
                startActivity(i)
                activity?.finish()
                Toast.makeText(context, "ออกจากระบบสำเร็จ", Toast.LENGTH_LONG).show()
            }
            myBuilder.show()
        }

        bindingProfile.swipe.setOnRefreshListener {
            var getAccount_id = arguments?.getString("account_id")

            bindingProfile.editProfile.setOnClickListener() {
                val i = Intent(activity, EditprofileActivity::class.java)
                i.putExtra("account_id", getAccount_id)
                i.putExtra("account_name", bindingProfile.usernameProfile.text)
                i.putExtra("account_email", bindingProfile.emailProfile.text)
                i.putExtra("account_birthday", bindingProfile.birthdayProfile.text)
                i.putExtra("account_gender", bindingProfile.genderProfile.text)
                i.putExtra("account_tel", bindingProfile.telProfile.text)
                i.putExtra("account_address", bindingProfile.addressProfile.text)
                startActivity(i)
            }

            val api: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            api.getProfile(
                getAccount_id.toString().toInt()
            ).enqueue(object : Callback<List<Account>> {
                override fun onResponse(
                    call: Call<List<Account>>,
                    response: Response<List<Account>>
                ) {
                    response.body()?.forEach {
                        profile_arr.add(0, it.account_name)
                        profile_arr.add(1, it.account_email)
                        profile_arr.add(2, it.account_birthday)
                        profile_arr.add(3, it.account_gender)
                        if (it.account_tel.isNullOrBlank()) {
                            profile_arr.add(4, "")
                        } else {
                            profile_arr.add(4, it.account_tel)
                        }
                        if (it.account_address.isNullOrBlank()) {
                            profile_arr.add(5, "")
                        } else {
                            profile_arr.add(5, it.account_address)
                        }
                    }
                    bindingProfile.usernameProfile.text = profile_arr[0].toString()
                    bindingProfile.emailProfile.text = profile_arr[1].toString()
                    bindingProfile.birthdayProfile.text = profile_arr[2].toString()
                    bindingProfile.genderProfile.text = profile_arr[3].toString()
                    bindingProfile.telProfile.text = profile_arr[4].toString()
                    bindingProfile.addressProfile.text = profile_arr[5].toString()
                }

                override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })

            bindingProfile.logout.setOnClickListener {

                val mDialogView =
                    LayoutInflater.from(context).inflate(R.layout.logout_dialog_layout, null)
                val myBuilder = AlertDialog.Builder(context)
                myBuilder.setView(mDialogView)

                myBuilder.setPositiveButton("ยกเลิก") { dialog, which ->
                    dialog.dismiss()
                }

                myBuilder.setNegativeButton("ออกจากระบบ") { dialog, which ->
                    val i = Intent(context, LoginActivity::class.java)
                    startActivity(i)
                    activity?.finish()
                    Toast.makeText(context, "ออกจากระบบสำเร็จ", Toast.LENGTH_LONG).show()
                }
                myBuilder.show()
            }
            bindingProfile.swipe.isRefreshing = false
        }

        return bindingProfile.root
    }

    override fun onResume() {
        super.onResume()
        var getAccount_id = arguments?.getString("account_id")

        bindingProfile.editProfile.setOnClickListener() {
            val i = Intent(activity, EditprofileActivity::class.java)
            i.putExtra("account_id", getAccount_id)
            i.putExtra("account_name", bindingProfile.usernameProfile.text)
            i.putExtra("account_email", bindingProfile.emailProfile.text)
            i.putExtra("account_birthday", bindingProfile.birthdayProfile.text)
            i.putExtra("account_gender", bindingProfile.genderProfile.text)
            i.putExtra("account_tel", bindingProfile.telProfile.text)
            i.putExtra("account_address", bindingProfile.addressProfile.text)
            startActivity(i)
        }

        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.getProfile(
            getAccount_id.toString().toInt()
        ).enqueue(object : Callback<List<Account>> {
            override fun onResponse(
                call: Call<List<Account>>,
                response: Response<List<Account>>
            ) {
                response.body()?.forEach {
                    profile_arr.add(0, it.account_name)
                    profile_arr.add(1, it.account_email)
                    profile_arr.add(2, it.account_birthday)
                    profile_arr.add(3, it.account_gender)
                    if (it.account_tel.isNullOrBlank()) {
                        profile_arr.add(4, "")
                    } else {
                        profile_arr.add(4, it.account_tel)
                    }
                    if (it.account_address.isNullOrBlank()) {
                        profile_arr.add(5, "")
                    } else {
                        profile_arr.add(5, it.account_address)
                    }
                }
                bindingProfile.usernameProfile.text = profile_arr[0].toString()
                bindingProfile.emailProfile.text = profile_arr[1].toString()
                bindingProfile.birthdayProfile.text = profile_arr[2].toString()
                bindingProfile.genderProfile.text = profile_arr[3].toString()
                bindingProfile.telProfile.text = profile_arr[4].toString()
                bindingProfile.addressProfile.text = profile_arr[5].toString()
            }

            override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                return t.printStackTrace()
            }
        })

        bindingProfile.logout.setOnClickListener {

            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.logout_dialog_layout, null)
            val myBuilder = AlertDialog.Builder(context)
            myBuilder.setView(mDialogView)

            myBuilder.setPositiveButton("ยกเลิก") { dialog, which ->
                dialog.dismiss()
            }

            myBuilder.setNegativeButton("ออกจากระบบ") { dialog, which ->
                val i = Intent(context, LoginActivity::class.java)
                startActivity(i)
                activity?.finish()
                Toast.makeText(context, "ออกจากระบบสำเร็จ", Toast.LENGTH_LONG).show()
            }
            myBuilder.show()
        }
    }

}