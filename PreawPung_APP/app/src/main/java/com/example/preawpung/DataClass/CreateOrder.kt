package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreateOrder(
    @Expose
    @SerializedName("order_id") val order_id:Int,

    @Expose
    @SerializedName("order_total") val order_total:Float,

    @Expose
    @SerializedName("order_status") val order_status:Int,

    @Expose
    @SerializedName("order_date") val order_date:String,

    @Expose
    @SerializedName("order_time") val order_time:String,

    @Expose
    @SerializedName("order_tracking") val order_tracking:String,

    @Expose
    @SerializedName("account_id") val account_id:Int,

    @Expose
    @SerializedName("slip_id") val slip_id:Int,
) {
}