package com.example.sangeetsagarowner.NavigationMenuDrawer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.MainDashboard.Model.Brand_Model
import com.example.sangeetsagarowner.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*

class Product_Comparision : Fragment() {

    lateinit var database : DatabaseReference
    lateinit var brand : EditText
    lateinit var model : EditText
    lateinit var search : CardView
    lateinit var prog : ProgressBar
    lateinit var reset_prod : CardView
    lateinit var reset_brand : CardView

    var brand_sum = 0 as Int
    var sum = 0 as Int
    var max = 0 as Int
    var ans = "" as String
    var item_deleted = 0 as Int

    lateinit var item_sum : TextView
    lateinit var Brand_sum : TextView
    lateinit var heighest : TextView
    lateinit var heigh : TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.product_comparison, container, false)

        reset_prod = view.findViewById(R.id.card_reset_product)
        reset_brand = view.findViewById(R.id.card_reset_brand)
        brand = view.findViewById(R.id.Brand)
        model = view.findViewById(R.id.Item_Name)
        search = view.findViewById(R.id.show_comparison)
        prog = view.findViewById(R.id.progress_comparison)
        database = FirebaseDatabase.getInstance().getReference("Selling_Details")


        item_sum = view.findViewById(R.id.sold_textview_answer)
        Brand_sum = view.findViewById(R.id.sold_brand_textview_answer)
        heighest = view.findViewById(R.id.heighest_sold_item)
        heigh = view.findViewById(R.id.heighest_sold_text)

        reset_prod.visibility = View.INVISIBLE
        reset_brand.visibility = View.INVISIBLE



        reset_prod.setOnClickListener(View.OnClickListener {
            var bb = brand.text.toString()
            var md = model.text.toString()
            if (bb.length != 0 && md.length != 0) {
                MaterialAlertDialogBuilder(this.requireContext())
                    .setTitle("Alert")
                    .setMessage("By Reseting Product sold data will be deleted")
                    .setNegativeButton("Cancel") { dialog, which ->
                        Log.i("Message : ", "Canceled")
                    }
                    .setPositiveButton("Reset") { dialog, which ->
                        Log.i("Message : ", "Deleted")
                        item_deleted = 1
                        brand_sum = 0;
                        Brand_sum.setText("--")
                        item_sum.setText("--")
                        database!!.child(bb!!).child(md!!).removeValue()
                    }
                    .show()
            }
        })

        reset_brand.setOnClickListener(View.OnClickListener {
            var bb = brand.text.toString()
            var md = model.text.toString()
            if (bb.length != 0 && md.length != 0) {
                MaterialAlertDialogBuilder(this.requireContext())
                    .setTitle("Alert")
                    .setMessage("By Reseting all Brand sold data will be deleted")
                    .setNegativeButton("Cancel") { dialog, which ->
                        Log.i("Message : ", "Canceled")
                    }
                    .setPositiveButton("Reset") { dialog, which ->
                        Log.i("Message : ", "Deleted")
                        item_deleted = 1
                        brand_sum = 0;
                        Brand_sum.setText("--")
                        item_sum.setText("--")
                        database!!.child(bb!!).removeValue()
                    }
                    .show()
            }
        })

        search.setOnClickListener(View.OnClickListener {
            var bb = brand.text.toString()
            var md = model.text.toString()
            prog.visibility = View.VISIBLE
            brand_sum = 0;
            Brand_sum.setText("--")
            item_sum.setText("--")
            reset_prod.visibility = View.INVISIBLE
            reset_brand.visibility = View.INVISIBLE
            item_deleted = 0;
            if (bb.length == 0 && md.length == 0){
                Toast.makeText(activity, "Please enter the fields", Toast.LENGTH_SHORT).show()
                heigh.setText("")
                heighest.setText("")
                prog.visibility = View.INVISIBLE
            }
            else{
                database.child(bb).orderByKey().addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists() && item_deleted == 0){
                            for(h in snapshot.children){
                                var MODEL = h.key as String
                                take_model_sum(MODEL,bb)
                                if(MODEL.equals(md)){
                                    reset_prod.visibility = View.VISIBLE
                                    take_item_sum(md,bb)
                                }
                            }
                            heigh.setText("Heighest Revenue Product from this Brand")
                            reset_brand.visibility = View.VISIBLE
                            prog.visibility = View.INVISIBLE
                        }

                        else{
                            prog.visibility = View.INVISIBLE
                            if(item_deleted ==0 ) {
                                Toast.makeText(activity, "Above Brand Name is not added in Sold Product", Toast.LENGTH_SHORT).show()
                                heigh.setText("")
                                heighest.setText("")
                            }
                            prog.visibility = View.INVISIBLE
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        })

        return view
    }

    private fun take_model_sum(model: String, bb: String) {
        database.child(bb).child(model).orderByValue().addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists() && item_deleted == 0){
                    var Num = snapshot.child("Quantity").getValue() as String
                    Log.i("Number :",""+Num.toString().trim())
                    var num = Integer.parseInt(Num.toString().trim())
                    if(num >= max) {
                        max = num
                        ans = model
                        var peak = ans + ": " + max.toString()
                        heighest.setText(peak)
                    }
                    Log.i("Max : ",""+num)
                    brand_sum = brand_sum + num
                    var ss = brand_sum.toString()
                    Brand_sum.setText(ss)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun take_item_sum(md: String, bb: String) {
        database.child(bb).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists() && item_deleted == 0){
                    var Sum = snapshot.child(md!!).child("Quantity").getValue() as String
                    sum = Sum.toInt()
                    item_sum?.setText(snapshot.child(md!!).child("Quantity").getValue() as String)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}