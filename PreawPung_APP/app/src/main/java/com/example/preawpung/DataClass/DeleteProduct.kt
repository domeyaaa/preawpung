package com.example.preawpung.DataClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DeleteProduct(
    @Expose
    @SerializedName("order_id") val order_id:Int,

    @Expose
    @SerializedName("product_id") val product_id:Int,
) {
}