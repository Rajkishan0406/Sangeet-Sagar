package com.example.sangeetsagarowner.MainDashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.sangeetsagarowner.Authentication.LoginFragment
import com.example.sangeetsagarowner.MainDashboard.Adapter.ProductAdapter
import com.example.sangeetsagarowner.MainDashboard.Model.ProductModel
import com.example.sangeetsagarowner.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ItemDetailsFragment :Fragment(){

    lateinit var fb : FloatingActionButton
    lateinit var new_prod : New_Product_Addition
    lateinit var bun : Bundle
    lateinit var database : DatabaseReference
    lateinit var storage : StorageReference
    lateinit var recyclerview : RecyclerView
    lateinit var progress : ProgressBar
    lateinit var emp : LottieAnimationView
    lateinit var No : CardView
    lateinit var Yes : CardView
    lateinit var delete : CardView
    lateinit var token : String

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
        emp = view.findViewById(R.id.item_empty)
        delete = view.findViewById(R.id.delete_item_ask)
        Yes = view.findViewById(R.id.yes)
        No = view.findViewById(R.id.no)
        storage = name?.let { FirebaseStorage.getInstance().reference.child("images/pic.jpg").child(it) }!!

        val animation = AnimationUtils.loadAnimation(activity, R.anim.trans_gone_up)
        delete.startAnimation(animation)

        Handler().postDelayed({
            delete.visibility = View.INVISIBLE
        }, 3000)

        No.setOnClickListener(View.OnClickListener {
            delete.visibility = View.INVISIBLE
        })

        Yes.setOnClickListener(View.OnClickListener {
            MaterialAlertDialogBuilder(this.requireContext())
                    .setTitle("Alert")
                    .setMessage("This Action will deleted all the product in this section/Item")
                    .setNegativeButton("Cancel") { dialog, which ->
                        Log.i("Message : ", "Canceled")
                    }
                    .setPositiveButton("Delete") { dialog, which ->
                        Log.i("Message : ", "Deleted")
                        Toast.makeText(activity,"Plaese wait few seconds!!",Toast.LENGTH_SHORT).show()
                        if (name != null) {
                            storage.delete().addOnCompleteListener(OnCompleteListener{
                                database!!.child(name).removeValue()
                                var data : DatabaseReference
                                data = FirebaseDatabase.getInstance().getReference("list")
                                data.orderByValue().addValueEventListener(object : ValueEventListener{
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if(snapshot.exists()){
                                            for(h in snapshot.children) {
                                                var MODEL = h.key as String
                                                var variab = h.value as String
                                                Log.i(""+MODEL," "+variab)
                                                // Toast.makeText(activity,""+MODEL+" & "+variab,Toast.LENGTH_SHORT).show()
                                                if(variab.equals(name)) {
                                                    data.child(MODEL).removeValue()
                                                    setFragmentBack(DashboardFragment())
                                                    Toast.makeText(activity,"Item deleted successfully",Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    }
                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })

                            }).addOnFailureListener(OnFailureListener {
                                Log.i("Fail"," to delete image............")
                                setFragmentBack(DashboardFragment())
                            })

                        }
                    }
                    .show()
        })

        recyclerview = view.findViewById(R.id.product_recyclerview)
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
                        val adapter = ProductAdapter(itemname)
                        recyclerview.adapter = adapter
                        recyclerview.startLayoutAnimation()
                    }
                    else{
                        emp.visibility = View.VISIBLE
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

    private fun setFragmentBack(forgotFragment: DashboardFragment) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.commit()
        }
    }

}