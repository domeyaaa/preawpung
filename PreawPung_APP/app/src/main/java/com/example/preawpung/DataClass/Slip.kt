package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Slip(
    @Expose
    @SerializedName("slip_id") val slip_id: Int,

    @Expose
    @SerializedName("slip_bank_name") val slip_bank_name:String,

    @Expose
    @SerializedName("slip_bank_number") val slip_bank_number:Int,

    @Expose
    @SerializedName("slip_pic") val slip_pic:String,

    @Expose
    @SerializedName("slip_price") val slip_price:Float,

    @Expose
    @SerializedName("slip_date") val slip_date:String,

    @Expose
    @SerializedName("slip_time") val slip_time:String
    ) {
}