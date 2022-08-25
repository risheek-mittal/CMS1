package com.example.ecomm.`interface`

import com.example.ecomm.models.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApi {

    @Headers("Content-Type: application/json")
    @POST("register")
    fun addUser(@Body userData: UserInfo): Call<UserInfo>
}