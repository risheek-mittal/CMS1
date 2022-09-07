package com.example.ecomm.models

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
//    https://run.mocky.io/v3/1aa46e47-823f-4f58-bfc1-96a1aff910cc
    fun getRetroClient(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}