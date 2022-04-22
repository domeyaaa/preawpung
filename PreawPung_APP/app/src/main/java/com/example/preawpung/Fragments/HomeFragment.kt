package com.example.preawpung.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.preawpung.*
import com.example.preawpung.API.PreawpungAPI
import com.example.preawpung.Adapters.MySliderImageAdapter
import com.example.preawpung.Adapters.ProductsAdapter
import com.example.preawpung.DataClass.Product
import com.example.preawpung.databinding.FragmentHomeBinding
import com.smarteist.autoimageslider.SliderView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private lateinit var bindingHomeFragment: FragmentHomeBinding
    var productList = arrayListOf<Product>()
    var account_id = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingHomeFragment = FragmentHomeBinding.inflate(layoutInflater)
        val imageSlider = bindingHomeFragment.imageSlider
        val imageList: ArrayList<String> = ArrayList()
        var getAccount_id = arguments?.getString("account_id")

        bindingHomeFragment.searchBtn.setOnClickListener(){
            val i = Intent(activity,SearchActivity::class.java)
            i.putExtra("account_id",getAccount_id.toString())
            startActivity(i)
        }

        account_id = getAccount_id.toString()

        //SliderImage
        imageList.add("https://c0.wallpaperflare.com/preview/476/980/7/accessories-accessory-jewelry-ring.jpg")
        imageList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDQVb8tphdUjo_29jNDRm5FPPBKSmOYUh2iJ7GTbxnXyPUJAFOQpEUFNR930cgKu-zMRQ&usqp=CAU")
        imageList.add("https://c4.wallpaperflare.com/wallpaper/821/188/1022/necklace-heart-medallion-love-wallpaper-preview.jpg")
        imageList.add("https://c.wallhere.com/photos/95/40/bracelets_sonyalpha-836485.jpg!d")
        setImageInSlider(imageList, imageSlider)

        bindingHomeFragment.necklessBtn.setOnClickListener {
            val i = Intent(context,NecklaceActivity::class.java)
            i.putExtra("account_id",account_id)
            startActivity(i)
        }

        bindingHomeFragment.bangleBtn.setOnClickListener {
            val i = Intent(context,BangleActivity::class.java)
            i.putExtra("account_id",account_id)
            startActivity(i)
        }

        bindingHomeFragment.ringBtn.setOnClickListener {
            val i = Intent(context,RingActivity::class.java)
            i.putExtra("account_id",account_id)
            startActivity(i)
        }

        return bindingHomeFragment.root
    }

    override fun onResume() {
        super.onResume()
        callProductDetaildata()
    }


    fun callProductDetaildata() {
        productList.clear()
        val serv: PreawpungAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PreawpungAPI::class.java)
        serv.retrieProduct()
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    response.body()?.forEach {
                        productList.add(
                            Product(
                                account_id,
                                it.product_id,
                                it.product_name,
                                it.product_price,
                                it.product_pic,
                                it.product_stock_amount,
                                it.product_detail
                            )
                        )
                    }
                    bindingHomeFragment.recommendView.adapter =
                        ProductsAdapter(productList, requireContext())
                    bindingHomeFragment.recommendView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySliderImageAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

}