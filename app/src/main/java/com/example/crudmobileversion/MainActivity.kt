package com.example.crudmobileversion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login)
        val signupButton = findViewById<Button>(R.id.signup)
        val title = findViewById<TextView>(R.id.titlemain)
        val powered = findViewById<TextView>(R.id.poweredmain)
        val nest = findViewById<ImageView>(R.id.nestimage)
        val kotlinIm = findViewById<ImageView>(R.id.kotlinimage)

        val animationDown:Animation = AnimationUtils.loadAnimation(this,R.anim.downanim)
        val animationUp:Animation = AnimationUtils.loadAnimation(this, R.anim.upanim)

        title.startAnimation(animationDown)
        powered.startAnimation(animationDown)
        nest.startAnimation(animationUp)
        kotlinIm.startAnimation(animationUp)
        loginButton.startAnimation(animationUp)
        signupButton.startAnimation(animationUp)

        loginButton.setOnClickListener { goToLogin() }
        signupButton.setOnClickListener { goToSignup() }
    }

    private fun goToLogin(){
        val loginScreen = Intent(this, LoginActivity::class.java)
        startActivity(loginScreen)
    }

    private fun goToSignup(){
        val signupScreen = Intent(this, SignupActivity::class.java)
        startActivity(signupScreen)
    }
}