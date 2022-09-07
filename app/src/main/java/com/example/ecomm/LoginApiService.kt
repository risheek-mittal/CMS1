package com.example.ecomm

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.example.ecomm.`interface`.LoginApi
import com.example.ecomm.models.UserLogin
import com.example.ecomm.models.UserResponse
import com.example.ecomm.objects.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginApiService {
    fun addUser(context: Context, userData: UserLogin, onResult: (UserResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(LoginApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("",t.localizedMessage.toString())
//                    Log.e("",t.stackTrace.toString())
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    val addedUser = response.body()
                    val preferences: SharedPreferences =
                        context.getSharedPreferences("myPrefs", MODE_PRIVATE)
                    preferences.edit().putString("token", "login").commit()
                    println(addedUser.toString())
                    Log.e("","it is running")
                    onResult(addedUser)
                }
            }
        )
    }
}