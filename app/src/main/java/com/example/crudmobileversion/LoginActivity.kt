package com.example.crudmobileversion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val animationDown: Animation = AnimationUtils.loadAnimation(this,R.anim.downanim)
        val animationUp: Animation = AnimationUtils.loadAnimation(this, R.anim.upanim)

        val loginButton = findViewById<Button>(R.id.loginsubmit)
        val userInput = findViewById<EditText>(R.id.username)
        val passwordInput = findViewById<EditText>(R.id.password)
        val title = findViewById<TextView>(R.id.logintitle)

        title.startAnimation(animationDown)
        userInput.startAnimation(animationUp)
        passwordInput.startAnimation(animationUp)
        loginButton.startAnimation(animationUp)

        loginButton.setOnClickListener { validate() }
    }

    private fun validate(){
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        if (!username.equals("") && !password.equals("")){

            val queue = Volley.newRequestQueue(this)
            val url:String = "https://crud-backend-danixl30.herokuapp.com/user/authenticate"

            val jsonObject = JSONObject()
            jsonObject.put("username", username)
            jsonObject.put("password", password)

            val stringResponse = JsonObjectRequest(Request.Method.POST,url, jsonObject,
                    { response ->
                        var res = response.getString("msg")
                        if (res.equals("success")){
                            goToHome()
                        }else{
                            createAlert("Error", res)
                        }
                    },
                    { error ->
                        createAlert("Error", error.toString())
                    })
            queue.add(stringResponse)
        }else{
            createAlert("Error", "The user and password will not be empty")
        }
    }

    private fun createAlert(title:String,content:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setPositiveButton("Ok",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goToHome(){
        val homeScreen = Intent(this, HomeActivity::class.java)
        startActivity(homeScreen)
        finish()
    }

}