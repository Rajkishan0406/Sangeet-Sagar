package com.example.sangeetsagarowner.MainDashboard

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.jar.Attributes

class Edit_Product_Details : Fragment() {

    lateinit var bun : Bundle
    lateinit var model : TextView
    lateinit var brand : EditText
    lateinit var price : EditText
    lateinit var wright : EditText
    lateinit var power : EditText
    lateinit var Des : EditText
    lateinit var Ava : CardView
    lateinit var UnAva : CardView
    lateinit var submit : CardView
    lateinit var progress : ProgressBar
    lateinit var status : TextView
    var check = -1
    lateinit var image : ImageView
    lateinit var select : ImageView
    private var imageUri: Uri? = null
    private val pickImage = 100
    var checker = -1

    lateinit var database : DatabaseReference
    lateinit var storage : StorageReference
    lateinit var ss : StorageReference

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.editproductdetails,container,false)

        bun = Bundle()
        bun = this.requireArguments()
        var first : String? = bun.getString("1st_level")
        var second = bun.getString("2nd_level")

        model = view.findViewById(R.id.modelname)
        model.setText(second)
        Toast.makeText(activity,""+first+" "+second,Toast.LENGTH_SHORT).show()

        brand = view.findViewById(R.id.Brand_name)
        price = view.findViewById(R.id.Price_)
        wright = view.findViewById(R.id.Weight)
        power = view.findViewById(R.id.Power)
        status = view.findViewById(R.id.edit_available_cardview_status)
        progress = view.findViewById(R.id.progress_circular_product_addition)
        Des = view.findViewById(R.id.About_Model)
        Ava = view.findViewById(R.id.available_cardview)
        UnAva = view.findViewById(R.id.unavailable_cardview)
        submit = view.findViewById(R.id.update)
        image = view.findViewById(R.id.image)
        select = view.findViewById(R.id.select_image)


        database = FirebaseDatabase.getInstance().getReference("Products").child(first.toString())


        database.child(second.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    price?.setText(snapshot.child("Price").getValue() as String)
                    wright?.setText(snapshot.child("Weight").getValue() as String)
                    brand?.setText(snapshot.child("Brand").getValue() as String)
                    power?.setText(snapshot.child("Power").getValue() as String)
                    Des?.setText(snapshot.child("Describe").getValue() as String)
                    var helper = snapshot.child("Availability").getValue() as String
                    checker = 0;
                    progress.visibility = View.INVISIBLE
                    //storage
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        select.setOnClickListener(View.OnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        })


        Ava.setOnClickListener(View.OnClickListener {
            check = 1;
            status.setText("Available")
        })

        UnAva.setOnClickListener(View.OnClickListener {
            check = 0;
            status.setText("Unavailable")
        })


        submit.setOnClickListener(View.OnClickListener {
            if (checker == 0) {
                progress.visibility = View.VISIBLE
                var Brand = brand.text.toString()
                var Price = price.text.toString()
                var Weight = wright.text.toString()
                var Power = power.text.toString()
                var Describe = Des.text.toString()
                if (Brand.isEmpty() || Price.isEmpty() || Weight.isEmpty() || Power.isEmpty() || Describe.isEmpty()
                    || check == -1 || imageUri == null
                ) {
                    progress.visibility = View.INVISIBLE
                    Toast.makeText(activity, "Please fill all information ", Toast.LENGTH_SHORT).show()
                } else {
                    progress.visibility = View.VISIBLE
                    var data: DatabaseReference
                    data = FirebaseDatabase.getInstance().getReference("Products")
                    if (first.toString() != null) {
                        data = FirebaseDatabase.getInstance().getReference("Products")
                        data.child(first.toString()).child(second.toString()).child("Model")
                            .setValue(second.toString())
                        data.child(first.toString()).child(second.toString()).child("Price")
                            .setValue(Price.toString())
                        data.child(first.toString()).child(second.toString()).child("Brand")
                            .setValue(Brand.toString())
                        data.child(first.toString()).child(second.toString()).child("Weight")
                            .setValue(Weight.toString())
                        data.child(first.toString()).child(second.toString()).child("Power")
                            .setValue(Power.toString())
                        data.child(first.toString()).child(second.toString()).child("Describe")
                            .setValue(Describe.toString())
                        data.child(first.toString()).child(second.toString()).child("Availability")
                            .setValue(check.toString())
                    }
                    ss = FirebaseStorage.getInstance().reference.child("images/pic.jpg")
                        .child(first.toString()).child(second.toString())
                    ss.delete().addOnCompleteListener(OnCompleteListener {
                        storage = FirebaseStorage.getInstance().reference.child("images/pic.jpg")
                            .child(first.toString()).child(second.toString())
                        storeimage()
                        }
                    ).addOnFailureListener(OnFailureListener {
                        progress.visibility = View.INVISIBLE
                        Toast.makeText(activity,"Something went wrong with image please try again",Toast.LENGTH_SHORT).show()
                    })
                }

            }
        })

        return view
    }


    private fun storeimage() {
        imageUri?.let {
            storage.putFile(it).addOnSuccessListener {
                progress.visibility = View.INVISIBLE
                Toast.makeText(activity, "Product stored successfully", Toast.LENGTH_SHORT).show()
                progress.visibility = View.INVISIBLE
                Log.i("image upload : ","Successfull")
                setFragment(DashboardFragment())
            }
                .addOnFailureListener(){
                    progress.visibility = View.INVISIBLE
                    Toast.makeText(activity, "Some thing went wrong!! please try again later", Toast.LENGTH_SHORT).show()
                    progress.visibility = View.INVISIBLE
                    Log.i("image upload : ","Fail")
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            image.setImageURI(imageUri)
        }

    }

    private fun setFragment(forgotFragment: DashboardFragment) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.commit()
        }
    }

}