package com.example.sangeetsagarowner.NavigationMenuDrawer

import android.os.Bundle
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
    var total_sum = 0 as Int

    lateinit var item_sum : TextView
    lateinit var Brand_sum : TextView

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

        search.setOnClickListener(View.OnClickListener {
            var bb = brand.text.toString()
            var md = model.text.toString()
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
                            var ss = brand_sum.toString()
                            Brand_sum.setText(ss)
                            prog.visibility = View.INVISIBLE
                        }
                        else{
                            prog.visibility = View.INVISIBLE
                            Toast.makeText(activity,"Above Brand Name is not addes in Sold Product",Toast.LENGTH_SHORT).show()
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
        database.child(bb).child(model).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var Num = snapshot.getValue() as String
                    var num = Num.toInt()
                    brand_sum = brand_sum + num
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun take_item_sum(md: String, bb: String) {
        database.child(bb).child(md).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var Num = snapshot.getValue() as String
                    item_sum.setText(Num)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}