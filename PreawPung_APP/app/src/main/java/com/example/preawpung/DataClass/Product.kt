package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Product(
    @Expose
    @SerializedName("account_id") val account_id:String,

    @Expose
    @SerializedName("product_id") val product_id: Int,

    @Expose
    @SerializedName("product_name") val product_name: String,

    @Expose
    @SerializedName("product_price") val product_price: Float,

    @Expose
    @SerializedName("product_pic") val product_pic: String,

    @Expose
    @SerializedName("product_stock_amount") val product_stock_amount:Int,

    @Expose
    @SerializedName("product_detail") val product_detail : String
) {
}