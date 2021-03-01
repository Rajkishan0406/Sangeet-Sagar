package com.example.sangeetsagarowner.MainDashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.R

class ProductAdapter(var productlist : ArrayList<ProductModel>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_description_layout,parent,false)
        return ProductAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product :ProductModel = productlist[position]

        holder.mod.text = product.item_name
        holder.price.text = product.item_price

        holder.model.text = product.item_model
        if(product.item_availability.toString().equals("1"))
            holder.avalable.text = "Available"
        else
            holder.avalable.text = "Not Available"


        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Log.i("clicked item name : ",""+product.item_name)

            }

        })
    }

    override fun getItemCount(): Int {
        return productlist.size
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            var mod = itemView.findViewById(R.id.product_name) as TextView
            var price = itemView.findViewById(R.id.product_price) as TextView
            var model = itemView.findViewById(R.id.product_model) as TextView
            var avalable = itemView.findViewById(R.id.product_available) as TextView
            var card = itemView.findViewById(R.id.product_cardview) as CardView
    }

}