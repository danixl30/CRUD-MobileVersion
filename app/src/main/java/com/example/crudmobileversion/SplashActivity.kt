package com.example.crudmobileversion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animation1: Animation = AnimationUtils.loadAnimation(this ,R.anim.logoanim)
        val animation2: Animation = AnimationUtils.loadAnimation(this ,R.anim.animationame)

        val logo = findViewById<ImageView>(R.id.crudlogo)
        val text = findViewById<TextView>(R.id.crudtextsplash)

        logo.startAnimation(animation1)
        text.startAnimation(animation2)

        Handler().postDelayed(Runnable {
            val mainScreen = Intent(this, MainActivity::class.java)
            startActivity(mainScreen)
            finish()
        }, 4000)
    }
}