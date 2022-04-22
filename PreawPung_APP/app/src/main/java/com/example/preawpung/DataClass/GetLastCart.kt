package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetLastCart(
    @Expose
    @SerializedName("order_id") val order_id_last: Int
) {
}