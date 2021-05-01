package com.example.sangeetsagarowner.NavigationMenuDrawer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import com.google.firebase.database.*

class Product_Comparision : Fragment() {

    lateinit var database : DatabaseReference
    lateinit var brand : EditText
    lateinit var model : EditText
    lateinit var search : CardView
    lateinit var prog : ProgressBar

    var brand_sum = 0 as Int
    var sum = 0 as Int
    var max = 0 as Int
    var ans = "" as String

    lateinit var item_sum : TextView
    lateinit var Brand_sum : TextView
    lateinit var per : TextView
    lateinit var heighest : TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.product_comparison, container, false)

        brand = view.findViewById(R.id.Brand)
        model = view.findViewById(R.id.Item_Name)
        search = view.findViewById(R.id.show_comparison)
        prog = view.findViewById(R.id.progress_comparison)
        database = FirebaseDatabase.getInstance().getReference("Selling_Details")

        item_sum = view.findViewById(R.id.sold_textview_answer)
        Brand_sum = view.findViewById(R.id.sold_brand_textview_answer)
        per = view.findViewById(R.id.sold_all_percentage)
        heighest = view.findViewById(R.id.heighest_sold_item)

        search.setOnClickListener(View.OnClickListener {
            var bb = brand.text.toString()
            var md = model.text.toString()
            brand_sum = 0;
            Brand_sum.setText("--")
            item_sum.setText("--")
            per.setText("--")
            heighest.setText("No Brand Selected")
            if(bb.length == 0 && md.length == 0)
                Toast.makeText(activity,"Please enter the fields",Toast.LENGTH_SHORT).show()
            else{
                prog.visibility = View.VISIBLE
                database.child(bb).orderByKey().addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            for(h in snapshot.children){
                                var MODEL = h.key as String
                                take_model_sum(MODEL,bb)
                                if(MODEL.equals(md)){
                                    take_item_sum(md,bb)
                                }
                            }
                            prog.visibility = View.INVISIBLE
                        }

                        else{
                            prog.visibility = View.INVISIBLE
                            Toast.makeText(activity,"Above Brand Name is not added in Sold Product",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            prog.visibility = View.INVISIBLE
        })

        return view
    }

    private fun take_model_sum(model: String, bb: String) {
        database.child(bb).child(model).orderByValue().addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var Num = snapshot.child("Quantity").getValue() as String
                    var num = Num.toInt()
                    if(num > max) {
                        max = num
                        ans = model
                        var peak = ans + ":" + max.toString()
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
                if(snapshot.exists()){
                    var Sum = snapshot.child(md).child("Quantity").getValue() as String
                    sum = Sum.toInt()
                    item_sum?.setText(snapshot.child(md).child("Quantity").getValue() as String)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}