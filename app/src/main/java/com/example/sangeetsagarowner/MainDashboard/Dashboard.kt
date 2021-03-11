package com.example.sangeetsagarowner.MainDashboard

import android.drm.DrmStore
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.Authentication.LoginFragment
import com.example.sangeetsagarowner.NavigationMenuDrawer.Contact_Developer
import com.example.sangeetsagarowner.NavigationMenuDrawer.TimetableFragment
import com.example.sangeetsagarowner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

            setFragment(Brand_Name())



    }

    private fun setFragment(loginFragment: Brand_Name) {
        var ft: FragmentTransaction = supportFragmentManager.beginTransaction();
        ft.replace(R.id.dashboard_frame,loginFragment)
        ft.commit()
    }


}