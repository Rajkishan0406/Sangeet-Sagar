package com.example.sangeetsagarowner.MainDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R

class About_Owner : Fragment() {

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.about_owner,container,false)
    }

}