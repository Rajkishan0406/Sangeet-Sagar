package com.example.sangeetsagarowner.Customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.Customer.CDashboard.CItemDashboard
import com.example.sangeetsagarowner.MainDashboard.DashboardFragment
import com.example.sangeetsagarowner.R

class CustomerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        setFragment(CItemDashboard())

    }


    private fun setFragment(loginFragment: CItemDashboard) {
        var ft: FragmentTransaction = supportFragmentManager.beginTransaction();
        ft.replace(R.id.Customer_dashboard_frame,loginFragment)
        ft.commit()
    }

}