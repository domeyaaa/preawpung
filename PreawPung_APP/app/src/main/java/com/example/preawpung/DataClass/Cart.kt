package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.logging.SimpleFormatter

data class Cart(
    @Expose
    @SerializedName("order_id") val order_id:Int,

    @Expose
    @SerializedName("product_id")val product_id:Int,

    @Expose
    @SerializedName("product_name")val product_name:String,

    @Expose
    @SerializedName("product_pic")val product_pic:String,

    @Expose
    @SerializedName("product_detail")val product_detail:String,

    @Expose
    @SerializedName("product_price_tmm")val product_price_tmm:String,

    @Expose
    @SerializedName("product_amount")val product_amount:Int,

    @Expose
    @SerializedName("sum") val sum:Float,

) {
}