package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Logout(
    @Expose
    @SerializedName("message") val message:String,
) {
}