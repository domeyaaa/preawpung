package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OTP(
    @Expose
    @SerializedName("error")val error :Boolean,

    @Expose
    @SerializedName("ref")val ref :String,

    @Expose
    @SerializedName("otp")val otp:String
) {
}