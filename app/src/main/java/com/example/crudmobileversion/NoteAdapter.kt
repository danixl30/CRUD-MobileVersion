package com.example.crudmobileversion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class NoteAdapter (var list:ArrayList<Note>):RecyclerView.Adapter<NoteAdapter.viewHolder>(){

    lateinit var context: Context

    class viewHolder(view:View):RecyclerView.ViewHolder(view){

        fun bindItems(data:Note){
            val title = itemView.findViewById<TextView>(R.id.titlecard)
            val content = itemView.findViewById<TextView>(R.id.contentcard)
            val deleteButton = itemView.findViewById<Button>(R.id.dalatenote)
            val editButton = itemView.findViewById<Button>(R.id.editnotebutton)
            title.text = data.title
            content.text = data.content

            deleteButton.setOnClickListener {
                val queue = Volley.newRequestQueue(itemView.context)
                var url = "https://crud-backend-danixl30.herokuapp.com/notes/delete/${data.id}"
                val request = JsonObjectRequest(Request.Method.DELETE, url, null, {
                    //val home = findNavController(itemView.findViewById(HomeActivity) ,R.id.mobile_navigation).navgate(R.id.navigation_home)
                    Toast.makeText(itemView.context, "Note deleted", Toast.LENGTH_SHORT).show()
                    val homeScreen = Intent(itemView.context.applicationContext, HomeActivity::class.java)
                    itemView.context.startActivity(homeScreen)
                }, {
                    Toast.makeText(itemView.context, "Sorry, we have an error", Toast.LENGTH_SHORT).show()
                })
                queue.add(request)
            }
            editButton.setOnClickListener {
                //Toast.makeText(itemView.context, "Edit id is ${data.id}", Toast.LENGTH_SHORT).show()
                val editScreen = Intent(itemView.context.applicationContext, EditActivity::class.java)
                editScreen.putExtra("data", data.id)
                itemView.context.startActivity(editScreen)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)
        return viewHolder(v)
    }

    override fun onBindViewHolder(holder: NoteAdapter.viewHolder, position: Int) {
       setAnimation(holder.itemView, position)
        //holder.itemView.setOnClickListener{ onItemClicked(data[position])}
       holder.bindItems(list[position])
    }

    private fun setAnimation(itemView: View, position: Int) {
        var lastPosition = -1
        if(position > lastPosition){
            val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.cardanim)
            itemView.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}