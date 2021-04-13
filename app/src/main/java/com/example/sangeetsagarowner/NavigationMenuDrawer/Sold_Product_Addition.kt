package com.example.sangeetsagarowner.NavigationMenuDrawer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.R
import com.example.sangeetsagarowner.SettingFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Sold_Product_Addition : Fragment() {

    lateinit var mAuth : FirebaseAuth
    lateinit var database : DatabaseReference

    lateinit var brand : EditText
    lateinit var model : EditText
    lateinit var quantity : EditText
    lateinit var card : CardView
    lateinit var com_card : CardView
    lateinit var prog : ProgressBar
    var check = -1
    var run = 0;
    var added = 0


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.sold_product_addition, container, false)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Selling_Details")

        brand = view.findViewById(R.id.Brand_Name)
        model = view.findViewById(R.id.Model_Name)
        quantity = view.findViewById(R.id.Quantity)
        card = view.findViewById(R.id.add)
        com_card = view.findViewById(R.id.show_comparison)
        prog = view.findViewById(R.id.progressbar_sold_product_addition)

        card.setOnClickListener(View.OnClickListener {
                if(brand.text.toString().length>0 && model.text.toString().length > 0 && quantity.text.toString().length > 0){
                    prog.visibility = View.VISIBLE
                    run = 1;
                    check = 1;
                    added = 0;
                    condition_checker();
                }
            else{
                    Toast.makeText(activity,"Please enter all fields",Toast.LENGTH_SHORT).show()
                }
        })

        com_card.setOnClickListener(View.OnClickListener {
                setcompareFragment(Product_Comparision())
        })

        return view;
    }

    private fun condition_checker() {
        var bb = brand.text.toString()
        var md = model.text.toString()
        database.child(bb).orderByKey().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (h in snapshot.children) {
                        val name = h.key as String
                        Log.i("model name : "," "+bb+" "+name)
                        if (name.equals(md)) {
                            Log.i("Equal contion run","..")
                            check = 100
                            if(added == 0)
                                extraction(bb,md)
                        }
                    }
                    Log.i("Comes here"," "+added)
                    if(added == 0 && check!=100){
                        database.child(bb).child(md).child("Quantity").setValue(quantity.text.toString())
                        added = 1
                        Toast.makeText(activity,"Item added",Toast.LENGTH_SHORT).show()
                        prog.visibility = View.INVISIBLE
                        //setfragment(SettingFragment())
                    }
                }
                else{
                    if(added == 0 && check!=100){
                        database.child(bb).child(md).child("Quantity").setValue(quantity.text.toString())
                        added = 1
                        Toast.makeText(activity,"Item added",Toast.LENGTH_SHORT).show()
                        prog.visibility = View.INVISIBLE
                        //setfragment(SettingFragment())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        prog.visibility = View.INVISIBLE
    }

    private fun extraction(bb: String, md: String) {

        if (added == 0) {
            database.child(bb).child(md).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var num = snapshot.child("Quantity").getValue() as String
                        var t = (quantity.text.toString()).toInt()
                        var Num: Int = num.toInt() + t
                        Log.i("number is : ", "" + num + " & " + Num)
                        num = Num.toString()
                        Toast.makeText(activity, "Item quantity updated to", Toast.LENGTH_SHORT).show()
                        if(added == 0) {
                            database.child(bb).child(md).child("Quantity").setValue(num)
                            added = 1
                        }
                        //setfragment(SettingFragment())
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    private fun setfragment(forgotFragment: SettingFragment) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

    private fun setcompareFragment(forgotFragment: Product_Comparision) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

}