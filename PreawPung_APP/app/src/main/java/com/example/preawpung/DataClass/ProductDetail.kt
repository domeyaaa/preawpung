package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductDetail(
    @Expose
    @SerializedName("product_name") val product_name: String,

    @Expose
    @SerializedName("product_pic") val product_pic: String,

    @Expose
    @SerializedName("product_amount") val product_amount: Int,

    @Expose
    @SerializedName("product_price_tmm") val product_price_tmm: Float
) {

}