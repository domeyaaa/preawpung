package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Track(
    @Expose
    @SerializedName("order_id") val order_id :Int,

    @Expose
    @SerializedName("slip_pic") val slip_pic :String,

    @Expose
    @SerializedName("order_date") val order_date: String,

    @Expose
    @SerializedName("order_tracking") val order_tracking:String,
) {
}