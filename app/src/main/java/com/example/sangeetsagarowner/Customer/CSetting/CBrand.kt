package com.example.sangeetsagarowner.Customer.CSetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.sangeetsagarowner.Customer.CDashboard.Adapter.C_Brand_Adapter
import com.example.sangeetsagarowner.MainDashboard.Adapter.Brand_Adapter
import com.example.sangeetsagarowner.MainDashboard.Add_Brand_Name
import com.example.sangeetsagarowner.MainDashboard.Model.Brand_Model
import com.example.sangeetsagarowner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class CBrand : Fragment() {


    lateinit var recyclerView: RecyclerView
    lateinit var progress : ProgressBar
    lateinit var database : DatabaseReference
    lateinit var emp : LottieAnimationView

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.cbrand, container, false)


        database = FirebaseDatabase.getInstance().getReference("Brand")
        recyclerView = view.findViewById(R.id.brand_recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        progress = view.findViewById(R.id.brand_progressbar)
        emp = view.findViewById(R.id.cbrand_empty)

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
                    val adapter = C_Brand_Adapter(itemname)
                    recyclerView.adapter = adapter
                    recyclerView.startLayoutAnimation()
                }
                else{
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