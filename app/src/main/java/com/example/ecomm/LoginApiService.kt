package com.example.ecomm

import android.util.Log
import com.example.ecomm.`interface`.LoginApi
import com.example.ecomm.`interface`.RestApi
import com.example.ecomm.models.UserLogin
import com.example.ecomm.objects.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginApiService {
    fun addUser(userData: UserLogin, onResult: (UserLogin?) -> Unit){
        val retrofit = ServiceBuilder.buildService(LoginApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<UserLogin> {
                override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                    Log.e("",t.localizedMessage.toString())
//                    Log.e("",t.stackTrace.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<UserLogin>, response: Response<UserLogin>) {
                    val addedUser = response.body()
                    println(response.body())
                    Log.e("","it is running")
                    onResult(addedUser)
                }
            }
        )
    }
}