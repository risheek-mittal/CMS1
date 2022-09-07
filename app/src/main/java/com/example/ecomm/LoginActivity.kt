package com.example.ecomm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
            addDummyUser(this@LoginActivity, email.text.toString(),password.text.toString())

//            addDummyAddress()
            val intent = Intent(this, SplashScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        signupButton.setOnClickListener {
            finish()
        }
    }
    fun addDummyUser(context: Context, email:String, password:String) {
        val apiService = LoginApiService()
        val userInfo = UserLogin(
            uemail = email,
            upassword = password )

        apiService.addUser(context,userInfo) {
            if (it?.message != null) {
                Log.e("",it.toString())
                val preferences: SharedPreferences =
                    this.getSharedPreferences("myPrefs", MODE_PRIVATE)
                preferences.edit().putString("username", it.userpresent!!._id).commit()
                // it = newly added user parsed as response
                // it?.id = newly added user ID
            } else {
                Log.e("","Error registering new user")
            }
        }
    }
}