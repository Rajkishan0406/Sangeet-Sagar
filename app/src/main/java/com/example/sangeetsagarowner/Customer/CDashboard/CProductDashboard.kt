package com.example.sangeetsagarowner.Customer.CDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.sangeetsagarowner.R

class CProductDashboard : Fragment() {

    lateinit var bun : Bundle
    lateinit var emp : LottieAnimationView

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.cprodoctdashboard, container, false)

        bun = this.requireArguments()
        var name : String? = bun.getString("item")

        return view


    }

}