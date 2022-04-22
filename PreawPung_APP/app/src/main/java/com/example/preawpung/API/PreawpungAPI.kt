package com.example.preawpung.API

import com.example.preawpung.DataClass.*
import retrofit2.Call
import retrofit2.http.*

interface PreawpungAPI {

    @FormUrlEncoded
    @POST("reg")
    fun registerAcc(
        @Field("account_username") account_username: String,
        @Field("account_password") account_password: String,
        @Field("account_name") account_name: String,
        @Field("account_gender") account_gender: String,
        @Field("account_email") account_email: String,
        @Field("account_birthday") account_birthday: String,
        @Field("account_role") account_role: String,
    ): Call<Account>

    @FormUrlEncoded
    @POST("addcart")
    fun addCart(
        @Field("order_id") order_id: Int,
        @Field("product_id") product_id: Int,
        @Field("product_price_tmm") product_price_tmm: Float,
        @Field("product_amount") product_amount: Int,
    ): Call<AddCart>

    @FormUrlEncoded
    @POST("createorder")
    fun createorder(
        @Field("order_total") order_total: Float,
        @Field("order_status") order_status: Int,
        @Field("account_id") account_id: Int,
    ): Call<CreateOrder>

    @FormUrlEncoded
    @PUT("add/{order_id}/{product_id}")
    fun addAmount(
        @Path("order_id") order_id: Int,
        @Path("product_id") product_id: Int,
        @Field("ok") ok: String
    ): Call<Amount>

    @FormUrlEncoded
    @PUT("remove/{order_id}/{product_id}")
    fun removeAmount(
        @Path("order_id") order_id: Int,
        @Path("product_id") product_id: Int,
        @Field("ok") ok: String
    ): Call<Amount>

    @FormUrlEncoded
    @PUT("cancelorder/{order_id}")
    fun cancelOrder(
        @Path("order_id") order_id: Int,
        @Field("ok") ok: String
    ): Call<OrderDetail>

    @FormUrlEncoded

    @PUT("confirmorder/{order_id}/{order_date}/{order_time}")
    fun confirmOrder(
        @Path("order_id") order_id: Int,
        @Path("order_date") order_date: String,
        @Path("order_time") order_time: String,
        @Field("ok") ok: String
    ): Call<ConfirmOrder>

    @FormUrlEncoded
    @PUT("addslip")
    fun addSlip(
        @Field("slip_id") slip_id: Int,
        @Field("slip_bank_name") slip_bank_name: String,
        @Field("slip_bank_number") slip_bank_number: Int,
        @Field("slip_pic") slip_pic: String,
        @Field("slip_price") slip_price: Float,
        @Field("slip_date") slip_date: String,
        @Field("slip_time") slip_time: String
    ): Call<Slip>

    @FormUrlEncoded
    @PUT("checkslip/{order_id}")
    fun checkOrder(
        @Path("order_id") order_id: Int,
        @Field("ok") ok: String
    ): Call<Slip>

    @FormUrlEncoded
    @PUT("updatetracking/{order_id}")
    fun updateTracking(
        @Path("order_id") order_id: Int,
        @Field("order_tracking") order_tracking: String
    ): Call<Track>

    @FormUrlEncoded
    @PUT("updateprofilecus/{account_id}")
    fun updateProfileCus(
        @Path("account_id") account_id: Int,
        @Field("account_name") account_name: String,
        @Field("account_email") account_email: String,
        @Field("account_birthday") account_birthday: String,
        @Field("account_gender") account_gender: String,
        @Field("account_tel") account_tel: String,
        @Field("account_address") account_address: String,
    ): Call<Account>

    @FormUrlEncoded
    @PUT("updateprofileemp/{account_id}")
    fun updateProfileEmp(
        @Path("account_id") account_id: Int,
        @Field("account_name") account_name: String,
        @Field("account_email") account_email: String,
        @Field("account_birthday") account_birthday: String,
        @Field("account_gender") account_gender: String,
    ): Call<Account>

    @FormUrlEncoded
    @PUT("newpassword/{account_email}")
    fun newPassword(
        @Path("account_email") account_email:String,
        @Field("account_password") account_password: String,
    ):Call<Account>

    @GET("allproduct")
    fun retrieProduct(): Call<List<Product>>

    @GET("gethistory/{account_id}")
    fun gethistory(
        @Path("account_id") account_id: Int
    ): Call<List<History>>

    @GET("getproductdetail/{order_id}")
    fun getproductdetail(
        @Path("order_id") order_id: Int
    ): Call<List<ProductDetail>>

    @GET("getorderdetail/{order_id}")
    fun getorderdetail(
        @Path("order_id") order_id: Int
    ): Call<List<OrderDetail>>

    @GET("cart/{order_id}")
    fun cart(
        @Path("order_id") order_id: String,
    ): Call<List<Cart>>

    @GET("searchproduct/{searct_text}")
    fun searchProduct(
        @Path("searct_text") searct_text: String
    ): Call<List<Product>>

    @GET("getTotal/{order_id}")
    fun getTotal(
        @Path("order_id") order_id: String,
    ): Call<List<GetTotal>>

    @GET("getCart/{account_id}")
    fun getCart(
        @Path("account_id") account_id: Int,
    ): Call<List<getCart>>

    @GET("login/{username}/{password}")
    fun login(
        @Path("username") account_username: String,
        @Path("password") account_password: String,
    ): Call<List<Login>>


    @GET("getprofile/{account_id}")
    fun getProfile(
        @Path("account_id") account_id: Int
    ): Call<List<Account>>

    @GET("getprofileemp/{account_id}")
    fun getProfileEmp(
        @Path("account_id") account_id: Int
    ): Call<List<Account>>

    @GET("allpaid")
    fun getAllPaid(): Call<List<Check>>

    @GET("alltrack")
    fun getAllTrack(): Call<List<Track>>

    @GET("allorder")
    fun getAllOrder(): Call<List<Order>>

    @GET("checkdetail/{order_id}")
    fun getCheckDetail(
        @Path("order_id") order_id: Int
    ): Call<CheckDetail>

    @GET("hisdetail/{order_id}")
    fun getHisDetail(
        @Path("order_id") order_id: Int
    ): Call<HisDetail>

    @GET("searchcheck/{order_id}")
    fun searchCheck(
        @Path("order_id") order_id: Int
    ): Call<List<Check>>

    @GET("searchtrack/{order_id}")
    fun searchTrack(
        @Path("order_id") order_id: Int
    ): Call<List<Check>>

    @GET("searchorder/{order_id}")
    fun searchOrder(
        @Path("order_id") order_id: Int
    ): Call<List<Order>>

    @GET("getnecklace")
    fun getNecklace(): Call<List<Product>>

    @GET("getbangle")
    fun getBangle(): Call<List<Product>>

    @GET("getring")
    fun getRing(): Call<List<Product>>

    @GET("sendemail/{account_email}")
    fun sendEmail(
        @Path("account_email") account_email: String
    ): Call<OTP>

    @DELETE("delproduct/{order_id}/{product_id}")
    fun delProduct(
        @Path("order_id") order_id: Int,
        @Path("product_id") product_id: Int
    ): Call<DeleteProduct>
    
    @PUT("ordersuccess/{order_id}")
    fun orderSuccess(
        @Path("order_id") order: Int,
    ):Call<Order>
}