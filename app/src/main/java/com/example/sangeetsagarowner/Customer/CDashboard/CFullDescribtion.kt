package com.example.sangeetsagarowner.Customer.CDashboard

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class CFullDescribtion : Fragment() {

    lateinit var bun : Bundle
    lateinit var bundle : Bundle
    lateinit var model : TextView
    lateinit var price : TextView
    lateinit var weight : TextView
    lateinit var brand : TextView
    lateinit var power : TextView
    lateinit var about : TextView
    lateinit var card : CardView
    lateinit var avatext : TextView
    lateinit var image : ImageView
    lateinit var progress  : ProgressBar
    lateinit var model_name : String
    lateinit var token : String
    lateinit var card_main : CardView
    lateinit var card_img : CardView

    lateinit var sharedPreferences: SharedPreferences
    lateinit var database : DatabaseReference


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.cfulldescription, container, false)



        bun = Bundle()
        bun = this.requireArguments()
        var key : String? = bun.getString("item")

        card_main = view.findViewById(R.id.cmain_card_rough)
        card_img = view.findViewById(R.id.cimage_card)

        val animation = AnimationUtils.loadAnimation(activity, R.anim.card_down_up)
        card_main.startAnimation(animation)



        var pref = PreferenceManager.getDefaultSharedPreferences(activity)
        var item_father = pref.getString("Item_Father",null)

        image = view.findViewById(R.id.cmain_image)
        model = view.findViewById(R.id.cname)
        price = view.findViewById(R.id.cprice)
        weight = view.findViewById(R.id.cweight)
        brand = view.findViewById(R.id.cbrand)
        power = view.findViewById(R.id.cpower)
        about = view.findViewById(R.id.cabout)
        card = view.findViewById(R.id.cavailable_cardview)
        avatext = view.findViewById(R.id.cava_text)
        progress = view.findViewById(R.id.cprogress_full)

        progress.visibility = View.VISIBLE

        database = item_father?.let { FirebaseDatabase.getInstance().getReference("Products").child(it) }!!


        database.child(key.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    model?.setText(snapshot.child("Model").getValue() as String)
                    model_name = model.text.toString()
                    price?.setText(snapshot.child("Price").getValue() as String)
                    weight?.setText(snapshot.child("Weight").getValue() as String)
                    brand?.setText(snapshot.child("Brand").getValue() as String)
                    power?.setText(snapshot.child("Power").getValue() as String)
                    about?.setText(snapshot.child("Describe").getValue() as String)
                    token = snapshot.child("Token").getValue() as String
                    Picasso.get().load(token).into(image)
                    var checker = snapshot.child("Availability").getValue() as String
                    if(checker.equals("0")){
                        card.setCardBackgroundColor(Color.RED)
                        avatext.setText("Not Available")
                    }
                }
                else
                {
                    Toast.makeText(activity,""+item_father+"  "+key,Toast.LENGTH_SHORT).show()
                }
                progress.visibility = View.INVISIBLE
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



        return view

    }

}