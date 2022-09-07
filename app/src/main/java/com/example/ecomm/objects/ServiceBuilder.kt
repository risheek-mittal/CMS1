package com.example.ecomm.objects

import com.example.ecomm.`interface`.EditAddressApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000") // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

    val api : EditAddressApi by lazy{
        retrofit.create(EditAddressApi::class.java)
    }
}