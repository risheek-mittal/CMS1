package com.example.ecomm

import android.util.Log
import com.example.ecomm.`interface`.OrderApi
import com.example.ecomm.`interface`.RestApi
import com.example.ecomm.models.OrderModel
import com.example.ecomm.models.UserInfoResponse
import com.example.ecomm.objects.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderApiService {
    fun addUser(userData: OrderModel, onResult: (OrderModel?) -> Unit){
        val retrofit = ServiceBuilder.buildService(OrderApi::class.java)
        retrofit.addOrder(userData).enqueue(
            object : Callback<OrderModel> {
                override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                    Log.e("",t.localizedMessage.toString())
//                    Log.e("",t.stackTrace.toString())
                    onResult(null)
                }
                override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                    val addedUser = response.body()
                    println(response.body())
                    Log.e("","it is running")
                    onResult(addedUser)
                }
            }
        )
    }
}