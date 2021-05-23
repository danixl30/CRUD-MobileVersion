package com.example.crudmobileversion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val animationDown: Animation = AnimationUtils.loadAnimation(this,R.anim.downanim)
        val animationUp: Animation = AnimationUtils.loadAnimation(this, R.anim.upanim)

        val signupButton = findViewById<Button>(R.id.signsubmit)
        val title = findViewById<TextView>(R.id.titlesign)
        val userInput = findViewById<EditText>(R.id.signUser)
        val passInput = findViewById<EditText>(R.id.passwordSign)
        val confInput = findViewById<EditText>(R.id.confirmpassword)

        title.startAnimation(animationDown)
        userInput.startAnimation(animationUp)
        passInput.startAnimation(animationUp)
        confInput.startAnimation(animationUp)
        signupButton.startAnimation(animationUp)

        signupButton.setOnClickListener { validate() }
    }

    private fun validate(){
        val username = findViewById<EditText>(R.id.signUser).text.toString()
        val password = findViewById<EditText>(R.id.passwordSign).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.confirmpassword).text.toString()

        if (!username.equals("") && !password.equals("") && !confirmPassword.equals("")){
            if (password.equals(confirmPassword)){
                val queue = Volley.newRequestQueue(this)
                val url:String = "https://crud-backend-danixl30.herokuapp.com/user/register"

                val jsonObject = JSONObject()
                jsonObject.put("username", username)
                jsonObject.put("password", password)
                jsonObject.put("confirm_password", confirmPassword)

                val stringResponse = JsonObjectRequest(
                    Request.Method.POST,url, jsonObject,
                        { response ->
                            var res = response.getString("msg")
                            if(res.equals("success")){
                                Toast.makeText(this, "New user created successfully", Toast.LENGTH_SHORT).show()
                                goToLogin()
                            }else{
                                createAlert("Error", res)
                            }
                        },
                        { error ->
                            createAlert("Error", error.toString())
                        })
                queue.add(stringResponse)
            }else{
                createAlert("Error", "The passwords must to be the same")
            }
        }else{
            createAlert("Error", "The boxes are empty")
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

    private fun goToLogin(){
        val loginScreen = Intent(this, LoginActivity::class.java)
        startActivity(loginScreen)
    }
}