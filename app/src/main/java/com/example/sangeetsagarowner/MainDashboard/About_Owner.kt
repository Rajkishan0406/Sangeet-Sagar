package com.example.sangeetsagarowner.MainDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R

class About_Owner : Fragment() {

    lateinit var name : TextView
    lateinit var c1 : CardView
    lateinit var c2 : CardView

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.about_owner,container,false)

        name = view.findViewById(R.id.about_name)
        c1 = view.findViewById(R.id.about_one)
        c2 = view.findViewById(R.id.about_two)

        //Animation
        val animatio = AnimationUtils.loadAnimation(activity, R.anim.trans_d_u)
        c1.startAnimation(animatio)
        c2.startAnimation(animatio)

        val animation = AnimationUtils.loadAnimation(activity,R.anim.fadein)
        name.startAnimation(animation)

        
        return view
    }

}