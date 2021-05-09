package com.example.sangeetsagarowner.Customer.CDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.Customer.CDashboard.Adapter.ItemAdapter
import com.example.sangeetsagarowner.Customer.CSetting.CSettingDashboard
import com.example.sangeetsagarowner.MainDashboard.Adapter.UsersAdapter
import com.example.sangeetsagarowner.MainDashboard.Model.Users
import com.example.sangeetsagarowner.MainDashboard.New_Item_Addition
import com.example.sangeetsagarowner.R
import com.example.sangeetsagarowner.SettingFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class CItemDashboard : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var fb : FloatingActionButton
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
        savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.citemdashboard,container,false)


        database = FirebaseDatabase.getInstance().getReference("list")
        progress = view .findViewById(R.id.cprogress_circular_item)

        recyclerview = view.findViewById(R.id.citem_recycler_view)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        var itemname = ArrayList<Users>()

        database?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    itemname.clear()
                    for (h in snapshot.children) {
                        val name = h.getValue()
                        itemname.add(Users(name as String))
                    }
                    progress.visibility = View.INVISIBLE
                    val adapter = ItemAdapter(itemname)
                    recyclerview.adapter = adapter
                    recyclerview.startLayoutAnimation()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })

        fab = view.findViewById(R.id.cfab_setting)
        fab.setOnClickListener(View.OnClickListener {
            setFragmentSetting(CSettingDashboard())
        })



        return  view;
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    private fun setFragmentSetting(forgotFragment: CSettingDashboard) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.Customer_dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

}