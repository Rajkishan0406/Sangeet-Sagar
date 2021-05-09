package com.example.sangeetsagarowner.Customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.Customer.CDashboard.CItemDashboard
import com.example.sangeetsagarowner.MainDashboard.DashboardFragment
import com.example.sangeetsagarowner.R

class CustomerActivity : AppCompatActivity() {

    lateinit var s : TextView
    lateinit var ss : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        s = findViewById(R.id.S)
        ss = findViewById(R.id.SS)

        s.animate().apply {
            duration = 1400
            rotationXBy(360f)
            rotationYBy(360f)
        }.start()

        ss.animate().apply {
            duration = 1400
            rotationXBy(360f)
            rotationYBy(360f)
        }.start()

        setFragment(CItemDashboard())

    }


    private fun setFragment(loginFragment: CItemDashboard) {
        var ft: FragmentTransaction = supportFragmentManager.beginTransaction();
        ft.replace(R.id.Customer_dashboard_frame,loginFragment)
        ft.commit()
    }

}