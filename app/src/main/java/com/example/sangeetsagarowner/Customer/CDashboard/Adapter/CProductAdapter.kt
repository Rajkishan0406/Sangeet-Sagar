package com.example.sangeetsagarowner.Customer.CDashboard.Adapter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.Customer.CDashboard.CFullDescribtion
import com.example.sangeetsagarowner.MainDashboard.ItemFullDescription
import com.example.sangeetsagarowner.MainDashboard.Model.ProductModel
import com.example.sangeetsagarowner.R
import com.squareup.picasso.Picasso

class CProductAdapter(var productlist : ArrayList<ProductModel>) : RecyclerView.Adapter<CProductAdapter.ViewHolder>() {

    var last = -1 as Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_description_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product : ProductModel = productlist[position]

        holder.mod.text = product.item_name
        holder.price.text = product.item_price
        Picasso.get().load(product.Imagetoken).into(holder.image)


        holder.model.text = product.item_model
        if(product.item_availability.toString().equals("1"))
            holder.avalable.text = "Available"
        else {
            holder.avalable.text = "Not Available"
            holder.cardm.setCardBackgroundColor(Color.RED);
        }

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Log.i("clicked item name : ",""+product.item_name)
                var activity = v!!.context as AppCompatActivity
                val IDF = CFullDescribtion()
                var bun : Bundle
                bun = Bundle()
                bun.putString("item",product.item_name)
                IDF.arguments = bun
                activity.supportFragmentManager.beginTransaction().replace(R.id.Customer_dashboard_frame,IDF).addToBackStack(null).commit()
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
        var image = itemView.findViewById(R.id.product_image) as ImageView
        var card = itemView.findViewById(R.id.product_cardview) as CardView
        var cardm = itemView.findViewById(R.id.prod_ava_card) as CardView
    }

}