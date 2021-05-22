package com.example.sangeetsagarowner.MainDashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.net.UrlQuerySanitizer
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text
import java.net.URI
import java.net.URL

class ItemFullDescription :Fragment(){


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
    lateinit var progress  :ProgressBar
    lateinit var delete : CardView
    lateinit var edit : CardView
    lateinit var model_name : String

    lateinit var card_main : CardView

    lateinit var frame : FrameLayout

    lateinit var token : String

    lateinit var sharedPreferences: SharedPreferences
    lateinit var database : DatabaseReference
    lateinit var storage : StorageReference

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

        card_main = view.findViewById(R.id.main_card_rough)
        model = view.findViewById(R.id.name)

        val animation = AnimationUtils.loadAnimation(activity, R.anim.card_down_up)
        card_main.startAnimation(animation)

        val animationx = AnimationUtils.loadAnimation(activity, R.anim.trans_gone_up)
        model.startAnimation(animationx)

        image = view.findViewById(R.id.main_image)
        price = view.findViewById(R.id.price)
        weight = view.findViewById(R.id.weight)
        brand = view.findViewById(R.id.brand)
        power = view.findViewById(R.id.power)
        about = view.findViewById(R.id.about)
        card = view.findViewById(R.id.available_cardview)
        avatext = view.findViewById(R.id.ava_text)
        progress = view.findViewById(R.id.progress_full)
        delete = view.findViewById(R.id.delete)
        edit = view.findViewById(R.id.edit)

        progress.visibility = View.VISIBLE

        database = item_father?.let { FirebaseDatabase.getInstance().getReference("Products").child(it) }!!
        storage = key?.let { FirebaseStorage.getInstance().reference.child("images/pic.jpg").child(item_father).child(it) }!!


        database.child(key.toString()).addValueEventListener(object : ValueEventListener{
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
                progress.visibility = View.INVISIBLE
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        delete.setOnClickListener(View.OnClickListener {
            MaterialAlertDialogBuilder(this.requireContext())
                .setTitle("Alert")
                .setMessage("Are you sure you want to delete this model")
                .setNegativeButton("Cancel") { dialog, which ->
                    Log.i("Message : ", "Canceled")
                }
                .setPositiveButton("Delete") { dialog, which ->
                    Log.i("Message : ", "Deleted")
                    progress.visibility = View.VISIBLE
                    var data: DatabaseReference
                    var delete_it = 0 as Int
                    data = FirebaseDatabase.getInstance().getReference("Products").child(item_father).child(key.toString())
                    if (delete_it == 0) {
                        data.removeValue().addOnCompleteListener(OnCompleteListener {
                            storage.delete().addOnCompleteListener(OnCompleteListener {
                                progress.visibility = View.INVISIBLE
                                delete_it = 1
                                Toast.makeText(activity, "Product Deleted Successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(getActivity(), Dashboard::class.java)
                                getActivity()?.startActivity(intent)
                            }).addOnFailureListener(OnFailureListener {
                                Toast.makeText(activity, "Something went wrong in deleting image", Toast.LENGTH_SHORT).show()
                            })
                        }).addOnFailureListener(OnFailureListener {
                            progress.visibility = View.INVISIBLE
                            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                        })
                    }
                }
                .show()
        })

        edit.setOnClickListener(View.OnClickListener {
            bundle = Bundle()
            bundle.putString("1st_level",item_father)
            bundle.putString("2nd_level",key)
            var pp = Edit_Product_Details()
            pp.arguments = bundle
            setFragment(pp)

        })

        return view
    }

    private fun setFragment(forgotFragment: Edit_Product_Details) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

}
