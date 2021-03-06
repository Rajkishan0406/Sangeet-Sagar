package com.example.sangeetsagarowner.MainDashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.sangeetsagarowner.Authentication.LoginFragment
import com.example.sangeetsagarowner.MainDashboard.Adapter.Brand_Adapter
import com.example.sangeetsagarowner.MainDashboard.Model.Brand_Model
import com.example.sangeetsagarowner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class Brand_Name : Fragment() {

    lateinit var fb : FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var progress : ProgressBar
    lateinit var brand : Add_Brand_Name
    lateinit var database : DatabaseReference
    lateinit var emp : LottieAnimationView
    var gone = 0 as Int

    lateinit var frame : FrameLayout

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.brand_name,container,false)

        frame = view.findViewById(R.id.brand_owner)

        val animation = AnimationUtils.loadAnimation(activity, R.anim.fragment_transaction)
        frame.startAnimation(animation)

        fb = view.findViewById(R.id.brand_fab1)
        database = FirebaseDatabase.getInstance().getReference("Brand")
        recyclerView = view.findViewById(R.id.brand_recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        progress = view.findViewById(R.id.brand_progressbar)
        brand = Add_Brand_Name()
        emp = view.findViewById(R.id.brand_empty)

        fb.setOnClickListener(View.OnClickListener {
            brand.show(childFragmentManager,"bottom sheet for brand")
        })




        var itemname = ArrayList<Brand_Model>()

        database?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists()){
                    emp.visibility = View.INVISIBLE
                    itemname.clear()
                    for( h in snapshot.children){
                        val name = h.getValue()
                        itemname.add(Brand_Model(name as String))
                    }
                    progress.visibility = View.INVISIBLE
                    val adapter = Brand_Adapter(itemname)
                    recyclerView.adapter = adapter
                    recyclerView.startLayoutAnimation()
                }
                else{
                    itemname.clear()
                    val adapter = Brand_Adapter(itemname)
                    recyclerView.adapter = adapter
                    emp.visibility = View.VISIBLE
                }
                progress.visibility = View.INVISIBLE
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



        return view
    }


}