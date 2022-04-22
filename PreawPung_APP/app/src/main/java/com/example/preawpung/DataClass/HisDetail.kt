package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HisDetail(
    @Expose
    @SerializedName("account_name") val account_name: String,

    @Expose
    @SerializedName("account_tel") val account_tel: String,

    @Expose
    @SerializedName("account_address") val account_address: String,

    @Expose
    @SerializedName("slip_price") val slip_price: Float,

    @Expose
    @SerializedName("slip_bank_name") val slip_bank_name: String,

    @Expose
    @SerializedName("slip_bank_number") val slip_bank_number: String,

    @Expose
    @SerializedName("slip_pic") val slip_pic: String,

    @Expose
    @SerializedName("slip_date") val slip_date: String,

    @Expose
    @SerializedName("slip_time") val slip_time: String,

    @Expose
    @SerializedName("product_name") val product_name: String,

    @Expose
    @SerializedName("product_pic") val product_pic: String,

    @Expose
    @SerializedName("product_amount") val product_amount: Int,

    @Expose
    @SerializedName("order_tracking")
    val order_tracking: String,
)
{
}