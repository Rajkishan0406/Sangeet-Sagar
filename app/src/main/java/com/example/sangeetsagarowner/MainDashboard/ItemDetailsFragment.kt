package com.example.sangeetsagarowner.MainDashboard

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sangeetsagarowner.MainDashboard.Adapter.ProductAdapter
import com.example.sangeetsagarowner.MainDashboard.Model.ProductModel
import com.example.sangeetsagarowner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ItemDetailsFragment :Fragment(){

    lateinit var fb : FloatingActionButton
    lateinit var new_prod : New_Product_Addition
    lateinit var bun : Bundle
    lateinit var database : DatabaseReference
    lateinit var recyclerview : RecyclerView
    lateinit var progress : ProgressBar

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.item_details_fragment,container,false)

        bun = this.requireArguments()
        var name : String? = bun.getString("item")

        var pref = PreferenceManager.getDefaultSharedPreferences(activity)
        pref.apply {
            val item_father = name.toString()
            val editor = pref.edit()
            editor.putString("Item_Father",item_father)
            editor.apply()
        }


        progress = view.findViewById(R.id.product_progressbar)
        database = FirebaseDatabase.getInstance().getReference("Products")

        recyclerview = view.findViewById(R.id.product_recyclerview)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        var itemname = ArrayList<ProductModel>()

        if (name != null) {
            database.child(name.toString())?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot!!.exists()){
                        itemname.clear()
                        for( h in snapshot.children){
                            val model_name = h.child("Model").getValue() as? String
                            val model_price = "Price " + h.child("Price").getValue() as? String
                            val model_brand = h.child("Brand").getValue() as? String
                            val model_available = h.child("Availability").getValue() as? String
                            itemname.add(ProductModel(model_name,model_price, model_brand, model_available))
                        }
                        progress.visibility = View.INVISIBLE
                        val adapter = ProductAdapter(itemname)
                        recyclerview.adapter = adapter
                        recyclerview.startLayoutAnimation()
                    }
                    progress.visibility = View.INVISIBLE
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

        Log.i("Item Name: ",""+name)

        fb = view.findViewById(R.id.fab1)
        new_prod = New_Product_Addition()

        fb.setOnClickListener(View.OnClickListener {
            var bundle : Bundle
            bundle = Bundle()
            bundle.putString("item",name)
            var pp = New_Product_Addition()
            pp.arguments = bundle
            setFragment(pp)
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