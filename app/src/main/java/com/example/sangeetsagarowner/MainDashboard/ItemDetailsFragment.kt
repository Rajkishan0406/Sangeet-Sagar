package com.example.sangeetsagarowner.MainDashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.Authentication.ForgotPassword
import com.example.sangeetsagarowner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemDetailsFragment :Fragment(){

    lateinit var fb : FloatingActionButton
    lateinit var new_prod : New_Product_Addition
    lateinit var bun : Bundle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.item_details_fragment,container,false)

        bun = Bundle()
        bun = this.requireArguments()
        var name : String? = bun.getString("item")

        Toast.makeText(activity,"Item Name : "+name,Toast.LENGTH_SHORT).show()
        Log.i("Item Name: ",""+name)

        fb = view.findViewById(R.id.fab1)
        new_prod = New_Product_Addition()

        fb.setOnClickListener(View.OnClickListener {
            setFragment(New_Product_Addition())
        })

        return view
    }

    private fun setFragment(forgotFragment: New_Product_Addition) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

}