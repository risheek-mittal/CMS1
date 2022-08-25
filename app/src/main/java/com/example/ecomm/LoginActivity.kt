package com.example.ecomm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ecomm.models.UserInfo
import com.example.ecomm.models.UserLogin
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        image_button.clipToOutline = true
        image_button.setOnClickListener {
            addDummyUser()
        }
        signupButton.setOnClickListener {
            finish()
        }
    }
    fun addDummyUser() {
        val apiService = LoginApiService()
        val userInfo = UserLogin(
            email = "alex@gmail.com",
            password = "164E92FC-D37A-4946-81CB-29DE7EE4B124" )

        apiService.addUser(userInfo) {
//            if (it?.fname != null) {
//                Log.e("",it.toString())
//                // it = newly added user parsed as response
//                // it?.id = newly added user ID
//            } else {
//                Log.e("","Error registering new user")
//            }
        }
    }
}