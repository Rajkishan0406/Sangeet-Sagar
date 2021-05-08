package com.example.sangeetsagarowner.Customer.CSetting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.MainDashboard.About_Owner
import com.example.sangeetsagarowner.NavigationMenuDrawer.TimetableFragment
import com.example.sangeetsagarowner.R
import com.google.firebase.auth.FirebaseAuth
import java.net.URL

class CSettingDashboard : Fragment() {

    lateinit var owner : CardView
    lateinit var time : CardView
    lateinit var brand : CardView
    lateinit var share : CardView

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.csettingfragment, container, false)

        owner = view.findViewById(R.id.c_owner)
        time = view.findViewById(R.id.c_time)
        brand = view.findViewById(R.id.c_brand_collection)
        share = view.findViewById(R.id.c_share)


        owner.setOnClickListener(View.OnClickListener {
            setownerSetting(About_Owner())
        })

        time.setOnClickListener(View.OnClickListener {
            setCTimeTable(CTimeTable())
        })

        brand.setOnClickListener(View.OnClickListener {
            setCbrandSetting(CBrand())
        })

        share.setOnClickListener(View.OnClickListener {
            var message : String = "rajkishan101@gmail.com"
            var intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share through :"))
        })

        return view;
    }

    private fun setownerSetting(forgotFragment: About_Owner) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.Customer_dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

    private fun setCbrandSetting(forgotFragment: CBrand) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.Customer_dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

    private fun setCTimeTable(forgotFragment: CTimeTable) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.Customer_dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }
}