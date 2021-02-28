package com.example.sangeetsagarowner.MainDashboard

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.renderscript.Script
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.jar.Attributes

class New_Product_Addition : Fragment(){

    lateinit var name : EditText
    lateinit var brand : EditText
    lateinit var price : EditText
    lateinit var wright : EditText
    lateinit var power : EditText
    lateinit var Des : EditText
    lateinit var Ava : CardView
    lateinit var UnAva : CardView
    lateinit var submit : CardView
    lateinit var progress : ProgressBar
    var check = -1
    lateinit var image : ImageView
    lateinit var select : ImageView
    private var imageUri: Uri? = null
    private val pickImage = 100
    lateinit var bun : Bundle
    var checker = 0
    lateinit var storage : StorageReference

    lateinit var database : DatabaseReference

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.new_product_addition, container, false)

        name = view.findViewById(R.id.Model_name)
        //name should be unique as it will be the reference..
        brand = view.findViewById(R.id.Brand_name)
        price = view.findViewById(R.id.Price_)
        wright = view.findViewById(R.id.Weight)
        power = view.findViewById(R.id.Power)
        progress = view.findViewById(R.id.progress_circular_product_addition)
        Des = view.findViewById(R.id.About_Model)
        Ava = view.findViewById(R.id.available_cardview)
        UnAva = view.findViewById(R.id.unavailable_cardview)
        submit = view.findViewById(R.id.done)
        image = view.findViewById(R.id.image)
        select = view.findViewById(R.id.select_image)

        bun = Bundle()
        bun = this.requireArguments()
        var key : String? = bun.getString("item")

        select.setOnClickListener(View.OnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        })


        database = FirebaseDatabase.getInstance().getReference("Products")
        storage = FirebaseStorage.getInstance().reference.child("images/pic.jpg").child(key.toString()).child(name.toString())

        Ava.setOnClickListener(View.OnClickListener {
            check = 1;
        })

        UnAva.setOnClickListener(View.OnClickListener {
            check = 0;
        })


        var Name = name.text
        var Brand = brand.text
        var Price = price.text
        var Weight = wright.text
        var Power = power.text
        var Describe = Des.text

        submit.setOnClickListener(View.OnClickListener {
            checker = 0
            if(Name.isEmpty() || Brand.isEmpty() || Price.isEmpty() || Weight.isEmpty() || Power.isEmpty() || Describe.isEmpty()
                    || check == -1 || imageUri == null)
            {
                Toast.makeText(activity,"Please fill all information ",Toast.LENGTH_SHORT).show()
            }
            else{
                progress.visibility = View.VISIBLE
                var found = 0;
                var end = 0;
                var ss = Name.toString()
                if (key != null) {
                    database.child(key).orderByKey().addValueEventListener(object : ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {

                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                for(h  in snapshot.children){
                                    val name = h.key
                                    if (name != null) {
                                        if(name.equals(ss))
                                            found = 1
                                    }
                                }
                                if(found == 1 && checker != -1) {
                                    Toast.makeText(activity,"Product Name already exit",Toast.LENGTH_SHORT).show()
                                    checker = -1
                                    progress.visibility = View.INVISIBLE
                                }
                                else {
                                    if (checker != -1) {
                                        var data: DatabaseReference
                                        data = FirebaseDatabase.getInstance().getReference("Products")
                                        if (key != null) {
                                            data = FirebaseDatabase.getInstance().getReference("Products")
                                            data.child(key).child(Name.toString()).child("Model").setValue(Name.toString())
                                            data.child(key).child(Name.toString()).child("Price").setValue(Price.toString())
                                            data.child(key).child(Name.toString()).child("Brand").setValue(Brand.toString())
                                            data.child(key).child(Name.toString()).child("Weight").setValue(Weight.toString())
                                            data.child(key).child(Name.toString()).child("Power").setValue(Power.toString())
                                            data.child(key).child(Name.toString()).child("Describe").setValue(Describe.toString())
                                            data.child(key).child(Name.toString()).child("Availability").setValue(check.toString())
                                        }
                                        //......storing image...........
                                        storeimage()
                                        Toast.makeText(activity, "Product stored successfully", Toast.LENGTH_SHORT).show()
                                        checker = -1
                                        progress.visibility = View.INVISIBLE
                                    }
                                }
                            }
                            else {
                                if (checker != -1) {
                                    if (found == 1) {
                                        Toast.makeText(activity, "Product Name already exit", Toast.LENGTH_SHORT).show()
                                        progress.visibility = View.INVISIBLE
                                    } else {
                                        var data: DatabaseReference
                                        data = FirebaseDatabase.getInstance().getReference("products")
                                        if (key != null) {
                                            data = FirebaseDatabase.getInstance().getReference("Products")
                                            data.child(key).child(Name.toString()).child("Model").setValue(Name.toString())
                                            data.child(key).child(Name.toString()).child("Price").setValue(Price.toString())
                                            data.child(key).child(Name.toString()).child("Brand").setValue(Brand.toString())
                                            data.child(key).child(Name.toString()).child("Weight").setValue(Weight.toString())
                                            data.child(key).child(Name.toString()).child("Power").setValue(Power.toString())
                                            data.child(key).child(Name.toString()).child("Describe").setValue(Describe.toString())
                                            data.child(key).child(Name.toString()).child("Availability").setValue(check.toString())
                                        }
                                        //......storing image...........
                                        storeimage()
                                        Toast.makeText(activity, "Product stored successfully", Toast.LENGTH_SHORT).show()
                                        checker = -1
                                        progress.visibility = View.INVISIBLE
                                    }
                                }
                            }
                        }
                    })
                }

            }
        })

        return view
    }

    private fun storeimage() {
        imageUri?.let {
            storage.putFile(it).addOnSuccessListener {
                    Log.i("image upload : ","Successfull")
            }
                    .addOnFailureListener(){
                        Log.i("image upload : ","Fail")
                    }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            image.setImageURI(imageUri)
        }

    }
}