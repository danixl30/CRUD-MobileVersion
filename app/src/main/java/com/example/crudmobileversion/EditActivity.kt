package com.example.crudmobileversion

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val queue = Volley.newRequestQueue(this)

        val animationDown: Animation = AnimationUtils.loadAnimation(this,R.anim.downanim)
        val animationUp: Animation = AnimationUtils.loadAnimation(this, R.anim.upanim)

        val titleText = findViewById<EditText>(R.id.edittitle)
        val contentText = findViewById<EditText>(R.id.editcontent)
        val updateButton = findViewById<Button>(R.id.updatebutton)
        val title = findViewById<TextView>(R.id.edittitle)

        title.startAnimation(animationDown)
        contentText.startAnimation(animationUp)
        titleText.startAnimation(animationUp)
        updateButton.startAnimation(animationUp)

        val id: String = getIntent().getStringExtra("data").toString()

        //createAlert(id, "")

        val url = "https://crud-backend-danixl30.herokuapp.com/notes/edit/${id}"

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest( url, null,
            { response ->
                val title = response.getString("title")
                val content = response.getString("content")
                titleText.setText(title)
                contentText.setText(content)
            },
            { error ->
                Toast.makeText(this, "Sorry, we have an error", Toast.LENGTH_SHORT).show()
                goToHome()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)


        updateButton.setOnClickListener { updateNote() }
    }

    private fun updateNote() {
        val title = findViewById<EditText>(R.id.edittitle).text.toString()
        val content = findViewById<EditText>(R.id.editcontent).text.toString()

        val id: String = getIntent().getStringExtra("data").toString()

        if (!title.equals("")){
            val queue = Volley.newRequestQueue(this)
            val url:String = "https://crud-backend-danixl30.herokuapp.com/notes/edit/${id}"

            val jsonObject = JSONObject()
            jsonObject.put("title", title)
            jsonObject.put("content", content)

            val stringResponse = JsonObjectRequest(
                Request.Method.PUT,url, jsonObject,
                { response ->
                    var res = response.getString("msg")
                    if (res.equals("success")){
                        Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
                        goToHome()
                    }else{
                        createAlert("Error", res)
                        goToHome()
                    }
                },
                { error ->
                    createAlert("Error", error.toString())
                })
            queue.add(stringResponse)
        }else{
            createAlert("Error", "The title not must to be empty")
        }
    }

    private fun goToHome(){
        val homeScreen = Intent(this, HomeActivity::class.java)
        startActivity(homeScreen)
        finish()
    }

    private fun createAlert(title:String,content:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setPositiveButton("Ok",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}