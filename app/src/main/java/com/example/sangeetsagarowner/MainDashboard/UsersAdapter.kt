package com.example.sangeetsagarowner.MainDashboard

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.R

class UsersAdapter(var userlist : ArrayList<Users>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>(){


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : Users = userlist[position]

        holder.texting.text = user.item
        holder.card.setOnClickListener(View.OnClickListener {
            Log.i("item clicked is : "," "+user.item)
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
