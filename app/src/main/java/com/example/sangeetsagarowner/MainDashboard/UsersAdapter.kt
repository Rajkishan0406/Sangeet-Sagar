package com.example.sangeetsagarowner.MainDashboard

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.R
import java.nio.BufferUnderflowException

class UsersAdapter(var userlist : ArrayList<Users>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>(){


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : Users = userlist[position]


        holder.texting.text = user.item
        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Log.i("item clicked was : "," "+user.item)
                var activity = v!!.context as AppCompatActivity
                val IDF = ItemDetailsFragment()
                var bun : Bundle
                bun = Bundle()
                bun.putString("item",user.item)
                IDF.arguments = bun
                activity.supportFragmentManager.beginTransaction().replace(R.id.dashboard_frame,IDF).addToBackStack(null).commit()
            }
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_layout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var texting = itemView.findViewById(R.id.item_name_of_product) as TextView
        var card  = itemView.findViewById(R.id.item_card) as CardView
        }

    }
