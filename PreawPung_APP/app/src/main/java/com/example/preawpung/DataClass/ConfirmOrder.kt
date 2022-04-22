package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ConfirmOrder(
    @Expose
    @SerializedName("order_id")val order_id :Int,

    @Expose
    @SerializedName("order_date") val order_date:String,

    @Expose
    @SerializedName("order_time") val order_time :String,

) {
}