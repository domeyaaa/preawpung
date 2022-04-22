package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Account(
    @Expose
    @SerializedName("account_id") val account_id:Int,

    @Expose
    @SerializedName("account_password") val account_password: String,

    @Expose
    @SerializedName("account_username") val account_username:String,

    @Expose
    @SerializedName("account_name") val account_name :String,

    @Expose
    @SerializedName("account_gender") val account_gender:String,

    @Expose
    @SerializedName("account_email") val account_email:String,

    @Expose
    @SerializedName("account_tel") val account_tel:String,

    @Expose
    @SerializedName("account_address") val account_address:String,

    @Expose
    @SerializedName("account_birthday") val account_birthday: String,

    @Expose
    @SerializedName("account_role") val account_role:String,

    @Expose
    @SerializedName("error")val error:Boolean

) {
}