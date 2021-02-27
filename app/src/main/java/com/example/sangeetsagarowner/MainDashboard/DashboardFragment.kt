package com.example.sangeetsagarowner.MainDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class DashboardFragment : Fragment(){

    lateinit var fb : FloatingActionButton
    lateinit var newitem : New_Item_Addition
    lateinit var database : DatabaseReference
    lateinit var recyclerview : RecyclerView

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View =  inflater.inflate(R.layout.dashboard_fragment, container, false)

        newitem = New_Item_Addition()
        database = FirebaseDatabase.getInstance().getReference("list")

        recyclerview = view.findViewById(R.id.item_recycler_view)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        var itemname = ArrayList<Users>()

        database?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists()){
                    itemname.clear()
                    for( h in snapshot.children){
                        val name = h.getValue()
                        itemname.add(Users(name as String))
                    }
                    val adapter = UsersAdapter(itemname)
                    recyclerview.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        fb = view.findViewById(R.id.fab)


        fb.setOnClickListener(View.OnClickListener {
           newitem.show(childFragmentManager,"bottom sheet")
        })

        return view;
    }

}

