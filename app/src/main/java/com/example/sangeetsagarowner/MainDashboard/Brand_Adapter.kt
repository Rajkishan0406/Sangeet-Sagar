package com.example.sangeetsagarowner.MainDashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.R

class Brand_Adapter(var brandlist : ArrayList<Brand_Model>) : RecyclerView.Adapter<Brand_Adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Brand_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.brand_name_layout,parent,false)
        return Brand_Adapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Brand_Adapter.ViewHolder, position: Int) {
        val brand : Brand_Model = brandlist[position]

        holder.texting.text = brand.brand
    }

    override fun getItemCount(): Int {
        return brandlist.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var texting = itemView.findViewById(R.id.item_name_of_product) as TextView
    }

}