package com.example.ecomm

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecomm.models.UserInfo
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setNavigationBarColor(Color.parseColor("#432fbf"))
        image_button.clipToOutline = true
        image_button.setOnClickListener {
            addDummyUser()
            var isValidEmail = isValidEmail(email.text.toString())
            var isValidName = isValidName(name.text.toString())
            var isValidPassword =
                passwordValidate(password.text.toString(), confirmPassword.text.toString())
//            if (isValidName && isValidEmail && isValidPassword) {
                val intent = Intent(this@MainActivity, SplashScreen::class.java)
                startActivity(intent)
//            }
//            else{
//
//            }
        }
        loginButton.setOnClickListener {

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)

        }
    }

    fun isValidName(str: String): Boolean {
        if (str.isEmpty()) {
            name.error = "Please Enter Name"
            Toast.makeText(this@MainActivity, "Please Enter Name", Toast.LENGTH_SHORT).show()
        }
        return str.isNotEmpty()
    }

    fun isValidEmail(str: String): Boolean {
        if (!EMAIL_ADDRESS_PATTERN.matcher(str).matches()) {
            email.error = "Invalid Email"
            Toast.makeText(this@MainActivity, "Invalid Email", Toast.LENGTH_SHORT).show()
        }
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    private fun passwordValidate(password1: String, password2: String): Boolean {
        when {
            password1.length < 8 -> {
                password.error = "Password Has To Be At Least 8 Characters Long"
                Toast.makeText(
                    this@MainActivity,
                    "Password Has To Be At Least 8 Characters Long",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            !password1.matches(".*[A-Z].*".toRegex()) -> {
                password.error = "Password Must Contain 1 Upper-case Character"
                Toast.makeText(
                    this@MainActivity,
                    "Password Must Contain 1 Upper-case Character",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            !password1.matches(".*[a-z].*".toRegex()) -> {
                password.error = "Password Must Contain 1 Lower-case Character"
                Toast.makeText(
                    this@MainActivity,
                    "Password Must Contain 1 Lower-case Character",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            !password1.matches(".*[!@#$%^&*+=/?].*".toRegex()) -> {
                password.error = "Password Must Contain 1 Symbol"
                Toast.makeText(
                    this@MainActivity,
                    "Password Must Contain 1 Symbol",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            password1 != password2 -> {
                confirmPassword.error = "Passwords Don't Match"
                Toast.makeText(this@MainActivity, "Passwords Don't Match", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            else -> return true
        }
    }

    fun addDummyUser() {
        val apiService = RestApiService()
        val userInfo = UserInfo(
            name = "Alex",
            email = "alex@gmail.com",
            password = "164E92FC-D37A-4946-81CB-29DE7EE4B124"
        )

        apiService.addUser(userInfo) {
            if (it?.name != null) {
                Log.e("", it.toString())
                // it = newly added user parsed as response
                // it?.id = newly added user ID
            } else {
//                Log.e("", "Error registering new user")
            }
        }
    }

}