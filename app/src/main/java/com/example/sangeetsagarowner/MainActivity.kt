package com.example.sangeetsagarowner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sangeetsagarowner.Authentication.AuthenticationActivity
import com.example.sangeetsagarowner.MainDashboard.Dashboard

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent(this,AuthenticationActivity::class.java)
        startActivity(intent)
    }
}