package com.example.sangeetsagarowner.Authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.R
import com.example.sangeetsagarowner.SplashFragment

class AuthenticationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        setFragment(SplashFragment())
    }

    private fun setFragment(loginFragment: SplashFragment) {
        var ft:FragmentTransaction = supportFragmentManager.beginTransaction();
        ft.replace(R.id.Auth_Frame,loginFragment)
        ft.commit()
    }


}