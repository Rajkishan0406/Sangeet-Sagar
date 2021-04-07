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
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.MainDashboard.Adapter.UsersAdapter
import com.example.sangeetsagarowner.MainDashboard.Model.Users
import com.example.sangeetsagarowner.R
import com.example.sangeetsagarowner.SettingFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class DashboardFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var fb : FloatingActionButton
    lateinit var newitem : New_Item_Addition
    lateinit var database : DatabaseReference
    lateinit var recyclerview : RecyclerView
    lateinit var progress : ProgressBar
    lateinit var fab : FloatingActionButton

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


        fab = view.findViewById(R.id.fab_setting)
        fab.setOnClickListener(View.OnClickListener {
            setFragmentSetting(SettingFragment())
        })

        return view;
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    private fun setFragmentSetting(forgotFragment: SettingFragment) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

}

