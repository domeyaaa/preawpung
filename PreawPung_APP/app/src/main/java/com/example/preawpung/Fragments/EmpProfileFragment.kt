package com.example.preawpung.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.DataClass.Account
import com.example.preawpung.EmpEditprofileActivity
import com.example.preawpung.LoginActivity
import com.example.preawpung.R
import com.example.preawpung.databinding.FragmentEmpProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class EmpProfileFragment : Fragment() {

    private lateinit var binding: FragmentEmpProfileBinding
    var profile_arr = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmpProfileBinding.inflate(layoutInflater)
        var getAccount_id = arguments?.getString("account_id")

        binding.editProfile.setOnClickListener {

            val i = Intent(activity, EmpEditprofileActivity::class.java)
            i.putExtra("account_id", getAccount_id)
            i.putExtra("name", binding.usernameProfile.text)
            i.putExtra("email", binding.emailProfile.text)
            i.putExtra("birthday", binding.birthdayProfile.text)
            i.putExtra("gender", binding.genderProfile.text)
            startActivity(i)
        }

        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.getProfileEmp(
            getAccount_id.toString().toInt()
        ).enqueue(object : Callback<List<Account>> {
            override fun onResponse(call: Call<List<Account>>, response: Response<List<Account>>) {
                response.body()?.forEach {
                    profile_arr.add(0, it.account_name)
                    profile_arr.add(1, it.account_email)
                    profile_arr.add(2, it.account_birthday)
                    profile_arr.add(3, it.account_gender)
                }
                binding.usernameProfile.text = profile_arr[0].toString()
                binding.emailProfile.text = profile_arr[1].toString()
                binding.birthdayProfile.text = profile_arr[2].toString()
                binding.genderProfile.text = profile_arr[3].toString()

            }

            override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                return t.printStackTrace()
            }
        })

        binding.logout.setOnClickListener {
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

        binding.swipe.setOnRefreshListener {
            var getAccount_id = arguments?.getString("account_id")

            binding.editProfile.setOnClickListener {

                val i = Intent(activity, EmpEditprofileActivity::class.java)
                i.putExtra("account_id", getAccount_id)
                i.putExtra("name", binding.usernameProfile.text)
                i.putExtra("email", binding.emailProfile.text)
                i.putExtra("birthday", binding.birthdayProfile.text)
                i.putExtra("gender", binding.genderProfile.text)
                startActivity(i)
            }

            val api: PreawpungAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PreawpungAPI::class.java)
            api.getProfileEmp(
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
                    }
                    binding.usernameProfile.text = profile_arr[0].toString()
                    binding.emailProfile.text = profile_arr[1].toString()
                    binding.birthdayProfile.text = profile_arr[2].toString()
                    binding.genderProfile.text = profile_arr[3].toString()

                }

                override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })

            binding.logout.setOnClickListener {
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
            binding.swipe.isRefreshing = false
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        var getAccount_id = arguments?.getString("account_id")

        binding.editProfile.setOnClickListener {

            val i = Intent(activity, EmpEditprofileActivity::class.java)
            i.putExtra("account_id", getAccount_id)
            i.putExtra("name", binding.usernameProfile.text)
            i.putExtra("email", binding.emailProfile.text)
            i.putExtra("birthday", binding.birthdayProfile.text)
            i.putExtra("gender", binding.genderProfile.text)
            startActivity(i)
        }

        val api: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        api.getProfileEmp(
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
                }
                binding.usernameProfile.text = profile_arr[0].toString()
                binding.emailProfile.text = profile_arr[1].toString()
                binding.birthdayProfile.text = profile_arr[2].toString()
                binding.genderProfile.text = profile_arr[3].toString()

            }

            override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                return t.printStackTrace()
            }
        })

        binding.logout.setOnClickListener {
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