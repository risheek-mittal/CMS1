package com.example.ecomm

import android.util.Log
import com.example.ecomm.`interface`.AddAddressApi
import com.example.ecomm.`interface`.AddressApi
import com.example.ecomm.`interface`.DeleteAddressApi
import com.example.ecomm.`interface`.EditAddressApi
import com.example.ecomm.models.*
import com.example.ecomm.objects.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressApiService {
    fun addUser( onResult: (List<UserAddress>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AddressApi::class.java)
        retrofit.addAddress().enqueue(
            object : Callback<List<UserAddress>> {
                override fun onFailure(call: Call<List<UserAddress>>, t: Throwable) {
                    Log.e("",t.localizedMessage.toString())
//                    Log.e("",t.stackTrace.toString())
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<List<UserAddress>>,
                    response: Response<List<UserAddress>>
                ) {
                    val addedUser = response.body()
//                    val login = LoginActivity()
//                    val preferences: SharedPreferences =
//                        login.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//                    preferences.edit().putString("token", addedUser!!.token).commit()
                    println(addedUser.toString())
                    Log.e("","it is running")
                    onResult(addedUser)
                }
            }
        )
    }
}

class AddAddressApiService {
    fun addUser(userInfo: AddAddress, onResult: (AddAddressResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AddAddressApi::class.java)
        retrofit.addAddress(userInfo).enqueue(
            object : Callback<AddAddressResponse> {
                override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                    Log.e("",t.localizedMessage.toString())
//                    Log.e("",t.stackTrace.toString())
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<AddAddressResponse>,
                    response: Response<AddAddressResponse>
                ) {
                    val addedUser = response.body()
//                    val login = LoginActivity()
//                    val preferences: SharedPreferences =
//                        login.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//                    preferences.edit().putString("token", addedUser!!.token).commit()
                    println(addedUser.toString())
                    Log.e("","it is running")
                    onResult(addedUser)
                }
            }
        )
    }
}
class EditAddressApiService {
    fun addUser(id: String, userInfo: AddAddress, onResult: (AddAddressResponse?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(EditAddressApi::class.java)
        retrofit.editAddress(id, userInfo).enqueue(
            object : Callback<AddAddressResponse> {
                override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                    Log.e("", t.localizedMessage.toString())
//                    Log.e("",t.stackTrace.toString())
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<AddAddressResponse>,
                    response: Response<AddAddressResponse>
                ) {
                    val addedUser = response.body()
//                    val login = LoginActivity()
//                    val preferences: SharedPreferences =
//                        login.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//                    preferences.edit().putString("token", addedUser!!.token).commit()
                    println(addedUser.toString())
                    Log.e("", "it is running")
                    onResult(addedUser)
                }
            }
        )
    }
}
    class DeleteAddressApiService {
        fun addUser(id: String, onResult: (DeleteAddressResponse?) -> Unit) {
            val retrofit = ServiceBuilder.buildService(DeleteAddressApi::class.java)
            retrofit.deleteAddress(id).enqueue(
                object : Callback<DeleteAddressResponse> {
                    override fun onFailure(call: Call<DeleteAddressResponse>, t: Throwable) {
                        Log.e("", t.localizedMessage.toString())
//                    Log.e("",t.stackTrace.toString())
                        onResult(null)
                    }

                    override fun onResponse(
                        call: Call<DeleteAddressResponse>,
                        response: Response<DeleteAddressResponse>
                    ) {
                        val addedUser = response.body()
//                    val login = LoginActivity()
//                    val preferences: SharedPreferences =
//                        login.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//                    preferences.edit().putString("token", addedUser!!.token).commit()
                        println(addedUser.toString())
                        Log.e("", "it is running")
                        onResult(addedUser)
                    }
                }
            )
        }
    }