package com.example.ecomm.`interface`

import com.example.ecomm.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface OrderApi {
    @Headers(
        "Content-Type: application/json",
        "cookies: ${Constants.TOKEN}"
    )
    @POST("inorder")
    fun addOrder(@Body userData: OrderModel): Call<OrderModel>

}