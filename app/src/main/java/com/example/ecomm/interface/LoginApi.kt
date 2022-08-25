package com.example.ecomm.`interface`

import com.example.ecomm.models.UserInfo
import com.example.ecomm.models.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun addUser(@Body userData: UserLogin): Call<UserLogin>
}