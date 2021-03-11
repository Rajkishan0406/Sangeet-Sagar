package com.example.sangeetsagarowner.MainDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Brand_Name : Fragment() {

    lateinit var fb : FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var progress : ProgressBar
    lateinit var brand : Add_Brand_Name

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.brand_name,container,false)

        fb = view.findViewById(R.id.brand_fab1)
        recyclerView = view.findViewById(R.id.brand_recyclerview)
        progress = view.findViewById(R.id.brand_progressbar)
        brand = Add_Brand_Name()

        fb.setOnClickListener(View.OnClickListener {
            brand.show(childFragmentManager,"bottom sheet for brand")
        })

        return view
    }

}