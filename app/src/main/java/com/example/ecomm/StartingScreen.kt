package com.example.ecomm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class StartingScreen : AppCompatActivity() {

    var anim: Animation? = null
    var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val preferences: SharedPreferences =
            this.getSharedPreferences("myPrefs", MODE_PRIVATE)
        var isLoggedIn = preferences.getString("token", "")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        imageView =
            findViewById<View>(R.id.imageView2) as ImageView // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_in
        ) // Create the animation.
        anim!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                if (isLoggedIn == "") {
                    val intent = Intent(this@StartingScreen, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent);
                } else{
                    val intent = Intent(this@StartingScreen, ThirdHomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent);                }

                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            override fun onAnimationRepeat(animation: Animation) {
                animation.repeatMode = Animation.INFINITE
                animation.repeatCount = 3
            }
        })
        imageView!!.startAnimation(anim)
    }
}