package com.example.sangeetsagarowner.MainDashboard

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.R
import com.google.android.material.transition.MaterialFade

class Dashboard : AppCompatActivity() {

    lateinit var s :TextView
    lateinit var ss : TextView
    lateinit var card : CardView



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

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

            setFragment(DashboardFragment())



    }

    private fun setFragment(loginFragment: DashboardFragment) {
        var ft: FragmentTransaction = supportFragmentManager.beginTransaction();
        ft.replace(R.id.dashboard_frame, loginFragment)
        ft.commit()
    }



}