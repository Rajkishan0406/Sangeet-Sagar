package com.example.sangeetsagarowner.MainDashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.*
import org.w3c.dom.Text

class ItemFullDescription :Fragment(){


    lateinit var bun : Bundle
    lateinit var model : TextView
    lateinit var price : TextView
    lateinit var weight : TextView
    lateinit var brand : TextView
    lateinit var power : TextView
    lateinit var about : TextView
    lateinit var card : CardView
    lateinit var avatext : TextView
    lateinit var progress  :ProgressBar
    lateinit var delete : CardView

    lateinit var sharedPreferences: SharedPreferences
    lateinit var database : DatabaseReference

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.item_full_description_fragment,container,false)

        bun = Bundle()
        bun = this.requireArguments()
        var key : String? = bun.getString("item")

        var pref = PreferenceManager.getDefaultSharedPreferences(activity)
        var item_father = pref.getString("Item_Father",null)

        model = view.findViewById(R.id.name)
        price = view.findViewById(R.id.price)
        weight = view.findViewById(R.id.weight)
        brand = view.findViewById(R.id.brand)
        power = view.findViewById(R.id.power)
        about = view.findViewById(R.id.about)
        card = view.findViewById(R.id.available_cardview)
        avatext = view.findViewById(R.id.ava_text)
        progress = view.findViewById(R.id.progress_full)
        delete = view.findViewById(R.id.delete)

        progress.visibility = View.VISIBLE

        database = item_father?.let { FirebaseDatabase.getInstance().getReference("Products").child(it) }!!

        database.child(key.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    model.setText(snapshot.child("Model").getValue() as String)
                    price.setText(snapshot.child("Price").getValue() as String)
                    weight.setText(snapshot.child("Weight").getValue() as String)
                    brand.setText(snapshot.child("Brand").getValue() as String)
                    power.setText(snapshot.child("Power").getValue() as String)
                    about.setText(snapshot.child("Describe").getValue() as String)
                    var checker = snapshot.child("Availability").getValue() as String
                    if(checker.equals("0")){
                        card.setCardBackgroundColor(Color.RED)
                        avatext.setText("Not Available")
                    }
                    progress.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        delete.setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            var data : DatabaseReference
            data = FirebaseDatabase.getInstance().getReference("Products").child(item_father).child(key.toString())
            data.removeValue().addOnCompleteListener(OnCompleteListener {
                progress.visibility = View.INVISIBLE
                Toast.makeText(activity,"Product Deleted Successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(getActivity(), Dashboard::class.java)
                getActivity()?.startActivity(intent)
            }).addOnFailureListener(OnFailureListener {
                progress.visibility = View.INVISIBLE
                Toast.makeText(activity,"Something went wrong",Toast.LENGTH_SHORT).show()
            })
        })

        return view
    }

}