package com.example.sangeetsagarowner.MainDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.MainDashboard.Adapter.UsersAdapter
import com.example.sangeetsagarowner.MainDashboard.Model.Users
import com.example.sangeetsagarowner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class DashboardFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var fb : FloatingActionButton
    lateinit var newitem : New_Item_Addition
    lateinit var database : DatabaseReference
    lateinit var recyclerview : RecyclerView
    lateinit var progress : ProgressBar
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout : DrawerLayout
    lateinit var navView : NavigationView
    lateinit var toolbar: Toolbar

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
        progress = view .findViewById(R.id.progress_circular_item)

        recyclerview = view.findViewById(R.id.item_recycler_view)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)


        //toggle = ActionBarDrawerToggle(activity,drawerLayout,R.string.open,R.string.close)
        //toggle.isDrawerIndicatorEnabled = true
        //drawerLayout.addDrawerListener(toggle)
        //toggle.syncState()

        var itemname = ArrayList<Users>()

        database?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists()){
                    itemname.clear()
                    for( h in snapshot.children){
                        val name = h.getValue()
                        itemname.add(Users(name as String))
                    }
                    progress.visibility = View.INVISIBLE
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}

