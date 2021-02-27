package com.example.sangeetsagarowner.MainDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class New_Product_Addition : Fragment(){

    lateinit var name : EditText
    lateinit var brand : EditText
    lateinit var price : EditText
    lateinit var wright : EditText
    lateinit var power : EditText
    lateinit var Des : EditText
    lateinit var Ava : CardView
    lateinit var UnAva : CardView
    lateinit var submit : CardView
    var check = -1

    lateinit var database : DatabaseReference

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.new_product_addition, container, false)

        name = view.findViewById(R.id.Model_name)
        //name should be unique as it will be the reference..
        brand = view.findViewById(R.id.Brand_name)
        price = view.findViewById(R.id.Price_)
        wright = view.findViewById(R.id.Weight)
        power = view.findViewById(R.id.Power)
        Des = view.findViewById(R.id.About_Model)
        Ava = view.findViewById(R.id.available_cardview)
        UnAva = view.findViewById(R.id.unavailable_cardview)
        submit = view.findViewById(R.id.done)

        database = FirebaseDatabase.getInstance().getReference("Products")

        Ava.setOnClickListener(View.OnClickListener {
            check = 1;
        })

        UnAva.setOnClickListener(View.OnClickListener {
            check = 0;
        })

        submit.setOnClickListener(View.OnClickListener {
            var Name = name.text
            var Brand = brand.text
            var Price = price.text
            var Weight = wright.text
            var Power = power.text
            var Describe = Des.text
            if(Name.isEmpty() || Brand.isEmpty() || Price.isEmpty() || Weight.isEmpty() || Power.isEmpty() || Describe.isEmpty() || check == -1)
            {

            }
            else{

            }
        })

        return view
    }

}