package com.example.sangeetsagarowner.MainDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R

class Edit_Product_Details : Fragment() {

    lateinit var bun : Bundle
    lateinit var model : TextView

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.editproductdetails,container,false)

        bun = Bundle()
        bun = this.requireArguments()
        var first : String? = bun.getString("1st_level")
        var second = bun.getString("2nd_level")

        model = view.findViewById(R.id.modelname)

        model.setText(second)

        Toast.makeText(activity,""+first+" "+second,Toast.LENGTH_SHORT).show()

        return view
    }

}