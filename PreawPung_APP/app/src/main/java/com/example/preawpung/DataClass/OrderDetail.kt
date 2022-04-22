package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrderDetail(
    @Expose
    @SerializedName("account_name") val account_name:String,

    @Expose
    @SerializedName("account_address") val account_address:String,

    @Expose
    @SerializedName("account_tel") val account_tel:String,

    @Expose
    @SerializedName("order_id") val order_id:Int,

    @Expose
    @SerializedName("order_total") val order_total:Float,

    @Expose
    @SerializedName("orderdetail_amount") val orderdetail_amount:Int,

    @Expose
    @SerializedName("order_time") val order_time :String,

    @Expose
    @SerializedName("order_date") val order_date:String,

    @Expose
    @SerializedName("order_tracking")val order_tracking:String,
) {
}