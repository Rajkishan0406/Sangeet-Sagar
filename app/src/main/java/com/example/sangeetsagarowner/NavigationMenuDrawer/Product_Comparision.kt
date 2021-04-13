package com.example.sangeetsagarowner.NavigationMenuDrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R

class Product_Comparision : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.product_comparison, container, false)

        return view
    }

    }