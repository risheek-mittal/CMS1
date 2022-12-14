package com.example.ecomm.`interface`

import com.example.ecomm.models.ApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ModelApi {
    @Headers(
        "Content-Type: application/json",
        "cookies: ${Constants.TOKEN}"
    )
    @GET("productlist")
    fun getQuote(): Call<List<ApiModel>>
}