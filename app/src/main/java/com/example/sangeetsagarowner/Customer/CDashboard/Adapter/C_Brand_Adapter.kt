package com.example.sangeetsagarowner.Customer.CDashboard.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.MainDashboard.Model.Brand_Model
import com.example.sangeetsagarowner.R

class C_Brand_Adapter(var brandlist : ArrayList<Brand_Model>) : RecyclerView.Adapter<C_Brand_Adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cbrand_list_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val brand : Brand_Model = brandlist[position]
        holder.texting.text = brand.brand

    }

    override fun getItemCount(): Int {
        return brandlist.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var texting = itemView.findViewById(R.id.item_name_of_product) as TextView
        var card  = itemView.findViewById(R.id.item_card) as CardView

    }
}