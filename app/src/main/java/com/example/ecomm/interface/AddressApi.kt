package com.example.ecomm.`interface`

import com.example.ecomm.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AddressApi {
    @Headers(
        "Content-Type: application/json",
        "cookies: ${Constants.TOKEN}"
    )
    @GET("address")
    fun addAddress(): Call<List<UserAddress>>


}
interface AddAddressApi {
    @Headers(
        "Content-Type: application/json",
        "cookies: ${Constants.TOKEN}"
    )
    @POST("addAddress")
    fun addAddress(@Body userData: AddAddress): Call<AddAddressResponse>


}
interface EditAddressApi {
    @Headers(
        "Content-Type: application/json",
        "cookies: ${Constants.TOKEN}"
    )
    suspend fun getHeader() :Response<String>
    @PUT("EditAddress/{addressid}")
    fun editAddress(@Path("addressid") id: String,@Body userData: AddAddress): Call<AddAddressResponse>


}

interface DeleteAddressApi {
    @Headers(
        "Content-Type: application/json",
        "cookies: ${Constants.TOKEN}"
    )
    suspend fun getHeader() :Response<String>
    @DELETE("address/{addressid}")
    fun deleteAddress(@Path("addressid") id: String): Call<DeleteAddressResponse>


}



object Constants {
    var id: String = "6315e2939120cf958652485f"
    const val TOKEN: String =  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MzA0N2JlOWY5YjQ2NjkxZTczNGQ1ZmQiLCJpYXQiOjE2NjI1MjUyNTUsImV4cCI6MTY2MjYxMTY1NX0.hWgkiaNU2ax-bbyouvjcTVAWmyKAMYvkxETOmYIW-jQ"
    }

