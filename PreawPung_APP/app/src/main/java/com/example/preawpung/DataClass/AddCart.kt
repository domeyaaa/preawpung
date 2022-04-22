package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddCart(
    @Expose
    @SerializedName("order_id")val order_id :Int,

    @Expose
    @SerializedName("product_id") val product_id:Int,

    @Expose
    @SerializedName("product_price_tmm") val product_price_tmm :Float,

    @Expose
    @SerializedName("product_amount")val product_amount:Int
) {
}