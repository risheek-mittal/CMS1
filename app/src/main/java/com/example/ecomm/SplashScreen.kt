package com.example.ecomm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {

    var anim: Animation? = null
    var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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
                startActivity(Intent(this@SplashScreen, ThirdHomeActivity::class.java))
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