package com.example.sangeetsagarowner.NavigationMenuDrawer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R

class TimetableFragment :Fragment(){

    lateinit var fb : CardView
    lateinit var newitem : Time_Update

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.timetable_fragment,container,false)


        fb = view.findViewById(R.id.update_Timing)

        newitem = Time_Update()


        fb.setOnClickListener(View.OnClickListener {
            newitem.show(childFragmentManager,"bottom sheet time updation")
        })

        return view
    }
}