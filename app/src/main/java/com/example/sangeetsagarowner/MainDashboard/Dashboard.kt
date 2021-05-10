package com.example.sangeetsagarowner.MainDashboard

import android.drm.DrmStore
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.Authentication.LoginFragment
import com.example.sangeetsagarowner.NavigationMenuDrawer.Contact_Developer
import com.example.sangeetsagarowner.NavigationMenuDrawer.TimetableFragment
import com.example.sangeetsagarowner.R
import com.example.sangeetsagarowner.SettingFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class Dashboard : AppCompatActivity() {

    lateinit var s :TextView
    lateinit var ss : TextView
    lateinit var card : CardView


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
        ft.replace(R.id.dashboard_frame,loginFragment)
        ft.commit()
    }



}