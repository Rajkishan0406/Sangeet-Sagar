package com.example.sangeetsagarowner.MainDashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import com.google.firebase.database.*
import org.w3c.dom.Text

class ItemFullDescription :Fragment(){


    lateinit var bun : Bundle
    lateinit var model : TextView
    lateinit var price : TextView
    lateinit var weight : TextView
    lateinit var brand : TextView
    lateinit var power : TextView
    lateinit var about : TextView

    lateinit var database : DatabaseReference

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.item_full_description_fragment,container,false)

        bun = Bundle()
        bun = this.requireArguments()
        var key : String? = bun.getString("item")

        model = view.findViewById(R.id.name)
        price = view.findViewById(R.id.price)
        weight = view.findViewById(R.id.weight)
        brand = view.findViewById(R.id.brand)
        power = view.findViewById(R.id.power)
        about = view.findViewById(R.id.about)

        database = FirebaseDatabase.getInstance().getReference("Products")


        Toast.makeText(activity,""+key,Toast.LENGTH_SHORT).show()

        return view
    }

}