package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AccountID(
    @Expose
    @SerializedName("account_id") val account_id:Int,
) {
}