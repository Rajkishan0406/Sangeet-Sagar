package com.example.sangeetsagarowner.Customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.Customer.CDashboard.CItemDashboard
import com.example.sangeetsagarowner.MainDashboard.DashboardFragment
import com.example.sangeetsagarowner.R

class CustomerActivity : AppCompatActivity() {

    lateinit var s : TextView
    lateinit var ss : TextView
    lateinit var card : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        s = findViewById(R.id.S)
        ss = findViewById(R.id.SS)

        card = findViewById(R.id.cardview)

        card.setOnClickListener(View.OnClickListener {
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
        })

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