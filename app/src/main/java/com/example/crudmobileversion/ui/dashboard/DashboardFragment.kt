package com.example.crudmobileversion.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudmobileversion.R
import org.json.JSONObject

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val animationDown: Animation = AnimationUtils.loadAnimation(activity,R.anim.downanim)
        val animationUp: Animation = AnimationUtils.loadAnimation(activity, R.anim.upanim)

        val saveButton = root.findViewById<Button>(R.id.CreateSave)
        val title = root.findViewById<TextView>(R.id.newnotetitle)
        val titleInput = root.findViewById<EditText>(R.id.CreateTitle)
        val content = root.findViewById<EditText>(R.id.CreateContent)

        title.startAnimation(animationDown)
        titleInput.startAnimation(animationUp)
        content.startAnimation(animationUp)
        saveButton.startAnimation(animationUp)

        saveButton.setOnClickListener { save(root) }
        return root
    }

    private fun save(root: View) {
        val title = root.findViewById<EditText>(R.id.CreateTitle).text.toString()
        val content = root.findViewById<EditText>(R.id.CreateContent).text.toString()

        if (!title.equals("")){
            val queue = Volley.newRequestQueue(activity)
            val url:String = "https://crud-backend-danixl30.herokuapp.com/notes/create"

            val jsonObject = JSONObject()
            jsonObject.put("title", title)
            jsonObject.put("content", content)

            val stringResponse = JsonObjectRequest(
                Request.Method.POST,url, jsonObject,
                { response ->
                    var res = response.getString("msg")
                    if (res.equals("sucess")){
                        Toast.makeText(context, "Note created successfully", Toast.LENGTH_SHORT).show()
                       val home = findNavController().navigate(R.id.navigation_home)
                    }else{
                        createAlert("Error", res)
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

    private fun createAlert(title:String,content:String){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setPositiveButton("Ok",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}