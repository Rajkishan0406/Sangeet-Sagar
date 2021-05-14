package com.example.sangeetsagarowner.Authentication

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.MainDashboard.Dashboard
import com.example.sangeetsagarowner.R
import com.example.sangeetsagarowner.SplashFragment
import com.google.firebase.auth.FirebaseAuth

class AuthenticationActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        auth = FirebaseAuth.getInstance()

        setFragment(SplashFragment())
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setFragment(loginFragment: SplashFragment) {
        var ft:FragmentTransaction = supportFragmentManager.beginTransaction();
        ft.replace(R.id.Auth_Frame,loginFragment)
        ft.commit()
    }


}