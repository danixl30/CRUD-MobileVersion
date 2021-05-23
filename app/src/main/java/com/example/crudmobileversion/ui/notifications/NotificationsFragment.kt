package com.example.crudmobileversion.ui.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudmobileversion.MainActivity
import com.example.crudmobileversion.R

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val animationDown: Animation = AnimationUtils.loadAnimation(activity,R.anim.downanim)
        val animationUp: Animation = AnimationUtils.loadAnimation(activity, R.anim.upanim)

        val logoutButton = root.findViewById<Button>(R.id.logout)
        val texthome = root.findViewById<TextView>(R.id.accountusername)
        val title = root.findViewById<TextView>(R.id.titleabout)
        val textview = root.findViewById<TextView>(R.id.textviewabout)
        val github = root.findViewById<TextView>(R.id.github)

        title.startAnimation(animationDown)
        texthome.startAnimation(animationUp)
        logoutButton.startAnimation(animationUp)
        textview.startAnimation(animationUp)

        getUser(root)

        github.setOnClickListener { goToGithub() }

        logoutButton.setOnClickListener { logout() }
        return root
    }

    private fun logout(){
        val queue = Volley.newRequestQueue(activity)

        val url = "https://crud-backend-danixl30.herokuapp.com/user/logout"

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest( url, null,
            { response ->
                val res = response.getString("msg")
                if (res.equals("")){
                    goToMain()
                }else{
                    goToMain()
                }
            },
            { error ->
                goToMain()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    private fun getUser(root: View) {
        val queue = Volley.newRequestQueue(activity)

        val texthome = root.findViewById<TextView>(R.id.accountusername)

        val url = "https://crud-backend-danixl30.herokuapp.com/user/session"

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest( url, null,
            { response ->
                val res = response.getString("msg")
                if (res.equals("")){
                    goToMain()
                }else{
                    texthome.setText("User: ${res}")
                }
            },
            { error ->
                goToMain()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    private fun goToMain(){
        val mainScreen = Intent(activity, MainActivity::class.java)
        startActivity(mainScreen)
    }

    private fun goToGithub(){
        val github = Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/danixl30/CRUD-MobileVersion"))
        startActivity(github)
    }
}