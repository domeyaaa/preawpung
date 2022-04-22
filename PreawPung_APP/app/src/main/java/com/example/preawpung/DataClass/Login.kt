package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Login (
    @Expose
    @SerializedName("account_id") val account_id:Int,

    @Expose
    @SerializedName("account_role") val account_role:String){
}