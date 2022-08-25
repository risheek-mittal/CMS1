package com.example.ecomm

import android.util.Log
import com.example.ecomm.`interface`.RestApi
import com.example.ecomm.models.UserInfo
import com.example.ecomm.objects.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    fun addUser(userData: UserInfo, onResult: (UserInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<UserInfo> {
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    Log.e("",t.localizedMessage.toString())
//                    Log.e("",t.stackTrace.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<UserInfo>, response: Response<UserInfo>) {
                    val addedUser = response.body()
                    println(response.body())
                    Log.e("","it is running")
                    onResult(addedUser)
                }
            }
        )
    }
}