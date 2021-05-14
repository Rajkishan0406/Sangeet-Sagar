package com.example.sangeetsagarowner.Customer.CDashboard

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.sangeetsagarowner.Customer.CDashboard.Adapter.CProductAdapter
import com.example.sangeetsagarowner.MainDashboard.Adapter.ProductAdapter
import com.example.sangeetsagarowner.MainDashboard.Model.ProductModel
import com.example.sangeetsagarowner.MainDashboard.New_Product_Addition
import com.example.sangeetsagarowner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CProductDashboard : Fragment() {

    lateinit var bun : Bundle
    lateinit var emp : LottieAnimationView
    lateinit var database : DatabaseReference
    lateinit var storage : StorageReference
    lateinit var recyclerview : RecyclerView
    lateinit var progress : ProgressBar
    lateinit var token : String

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

        var pref = PreferenceManager.getDefaultSharedPreferences(activity)
        pref.apply {
            val item_father = name.toString()
            val editor = pref.edit()
            editor.putString("Item_Father",item_father)
            editor.apply()
        }

        progress = view.findViewById(R.id.cproduct_progressbar)
        database = FirebaseDatabase.getInstance().getReference("Products")
        emp = view.findViewById(R.id.citem_empty)
        storage = name?.let { FirebaseStorage.getInstance().reference.child("images/pic.jpg").child(it) }!!

        recyclerview = view.findViewById(R.id.cproduct_recyclerview)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        var itemname = ArrayList<ProductModel>()

        if (name != null) {
            database.child(name.toString())?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot!!.exists()){
                        emp.visibility = View.INVISIBLE
                        itemname.clear()
                        for( h in snapshot.children){
                            val model_name = h.child("Model").getValue() as? String
                            val model_price = "Price " + h.child("Price").getValue() as? String
                            val model_brand = h.child("Brand").getValue() as? String
                            val model_available = h.child("Availability").getValue() as? String
                            val t = h.child("Token").getValue() as? String
                            itemname.add(ProductModel(model_name,model_price, model_brand, model_available,t))
                        }
                        progress.visibility = View.INVISIBLE
                        val adapter = CProductAdapter(itemname)
                        recyclerview.adapter = adapter
                        recyclerview.startLayoutAnimation()
                    }
                    else{
                        itemname.clear()
                        val adapter = CProductAdapter(itemname)
                        recyclerview.adapter = adapter
                        emp.visibility = View.VISIBLE
                    }
                    progress.visibility = View.INVISIBLE
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }



        return view


    }

}