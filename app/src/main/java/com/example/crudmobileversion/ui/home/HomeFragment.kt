package com.example.crudmobileversion.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudmobileversion.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val animationDown: Animation = AnimationUtils.loadAnimation(activity,R.anim.downanim)
        val animationUp: Animation = AnimationUtils.loadAnimation(activity, R.anim.upanim)

        val title = root.findViewById<TextView>(R.id.hometitle)
        val welcome = root.findViewById<TextView>(R.id.textViewWelcome)

        title.startAnimation(animationDown)
        welcome.startAnimation(animationUp)

        getUser(root)
        getNotes(root)

        return root
    }

    private fun getUser(root: View) {
        val queue = Volley.newRequestQueue(activity)

        val texthome = root.findViewById<TextView>(R.id.textViewWelcome)

        val url = "https://crud-backend-danixl30.herokuapp.com/user/session"

        // Request a JSONObject response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest( url, null,
            { response ->
                val res = response.getString("msg")
                if (res.equals("")){
                    goToMain()
                }else{
                   texthome.setText("Welcome ${res}")
                }
            },
            { error ->
               goToMain()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    @SuppressLint("WrongConstant")
    private fun getNotes(root:View){
        val recyclerView = root.findViewById<RecyclerView>(R.id.notesrecycler)
        val queue = Volley.newRequestQueue(activity)
        val url = "https://crud-backend-danixl30.herokuapp.com/notes"

        // Request a JSONObject response from the provided URL.
        val jsonObjectArrayRequest = JsonArrayRequest( url,
            { response: JSONArray ->
                try {
                    val res = response
                    if (res.length() > 0) {
                        var i: Int = 0
                        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
                        val notes = ArrayList<Note>()
                        for (i in 0 until res.length()) {
                            var data = res.getJSONObject(i)
                            //createAlert(data.getString("title"), data.getString("content"))
                            notes.add(Note(data.getString("title"), data.getString("content"), data.getString("_id")))
                        }
                        val adapter = NoteAdapter(notes)
                        recyclerView.adapter = adapter
                    }else{
                        val emptyText = root.findViewById<TextView>(R.id.emptytext)
                        emptyText.setText("You do not have notes, create a new one!!!")
                    }
                }catch (error: JSONException){
                    createAlert("error", error.toString())
                }

            },
            { error ->
                goToMain()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectArrayRequest)
    }

    private fun createAlert(title:String,content:String){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setPositiveButton("Ok",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goToMain(){
        val mainScreen = Intent(activity, MainActivity::class.java)
        startActivity(mainScreen)
    }


}