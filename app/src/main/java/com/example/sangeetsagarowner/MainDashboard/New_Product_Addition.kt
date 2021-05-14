package com.example.sangeetsagarowner.MainDashboard

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


@Suppress("UNREACHABLE_CODE")
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
    lateinit var status : TextView
    lateinit var progress : ProgressBar
    var check = -1
    lateinit var image : ImageView
    lateinit var select : ImageView
    private var imageUri: Uri? = null
    private val pickImage = 100
    lateinit var bun : Bundle
    var checker = 0
    lateinit var storage : StorageReference
    lateinit var storage_deletion : StorageReference
    var image_added = 0

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
        status = view.findViewById(R.id.available_cardview_status)
        select = view.findViewById(R.id.select_image)

        bun = Bundle()
        bun = this.requireArguments()
        var key : String? = bun.getString("item")

        select.setOnClickListener(View.OnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        })


        database = FirebaseDatabase.getInstance().getReference("Products")

        Ava.setOnClickListener(View.OnClickListener {
            check = 1;
            status.setHint("Product Available")
        })

        UnAva.setOnClickListener(View.OnClickListener {
            check = 0;
            status.setHint("Product Is Not Available")
        })


        var Name = name.text
        var Brand = brand.text
        var Price = price.text
        var Weight = wright.text
        var Power = power.text
        var Describe = Des.text

        submit.setOnClickListener(View.OnClickListener {
            checker = 0
            if (Name.isEmpty() || Brand.isEmpty() || Price.isEmpty() || Weight.isEmpty() || Power.isEmpty() || Describe.isEmpty()
                || check == -1 || imageUri == null
            ) {
                Toast.makeText(activity, "Please fill all information ", Toast.LENGTH_SHORT).show()
            } else {
                progress.visibility = View.VISIBLE
                var found = 0;
                var end = 0;
                var ss = Name.toString()
                if (key != null) {
                    database.child(key).orderByKey().addValueEventListener(object :
                        ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {

                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                for (h in snapshot.children) {
                                    val name = h.key
                                    if (name != null) {
                                        if (name.equals(ss))
                                            found = 1
                                    }
                                }
                                if (found == 1 && checker != -1) {
                                    Toast.makeText(
                                        activity,
                                        "Product Name already exit",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    checker = -1
                                    progress.visibility = View.INVISIBLE
                                } else {
                                    if (checker != -1) {
                                        var data: DatabaseReference
                                        data =
                                            FirebaseDatabase.getInstance().getReference("Products")
                                        if (key != null) {
                                            data = FirebaseDatabase.getInstance()
                                                .getReference("Products")
                                            data.child(key).child(Name.toString()).child("Model")
                                                .setValue(
                                                    Name.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Price")
                                                .setValue(
                                                    Price.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Brand")
                                                .setValue(
                                                    Brand.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Weight")
                                                .setValue(
                                                    Weight.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Power")
                                                .setValue(
                                                    Power.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Describe")
                                                .setValue(
                                                    Describe.toString()
                                                )
                                            data.child(key).child(Name.toString())
                                                .child("Availability").setValue(
                                                    check.toString()
                                                )
                                            data.child(key).child(Name.toString())
                                                .child("Image_Url").setValue(
                                                    imageUri.toString()
                                                )
                                        }
                                        //......storing image...........
                                        storage =
                                            FirebaseStorage.getInstance().reference.child("images/pic.jpg")
                                                .child(
                                                    key
                                                ).child(name.text.toString())
                                        deleteimgae(key,Name)
                                        storeimage(key, Name)
                                        checker = -1
                                    }
                                }
                            } else {
                                if (checker != -1) {
                                    if (found == 1) {
                                        Toast.makeText(
                                            activity,
                                            "Product Name already exit",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        progress.visibility = View.INVISIBLE
                                    } else {
                                        var data: DatabaseReference
                                        data =
                                            FirebaseDatabase.getInstance().getReference("products")
                                        if (key != null) {
                                            data = FirebaseDatabase.getInstance()
                                                .getReference("Products")
                                            data.child(key).child(Name.toString()).child("Model")
                                                .setValue(
                                                    Name.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Price")
                                                .setValue(
                                                    Price.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Brand")
                                                .setValue(
                                                    Brand.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Weight")
                                                .setValue(
                                                    Weight.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Power")
                                                .setValue(
                                                    Power.toString()
                                                )
                                            data.child(key).child(Name.toString()).child("Describe")
                                                .setValue(
                                                    Describe.toString()
                                                )
                                            data.child(key).child(Name.toString())
                                                .child("Availability").setValue(
                                                    check.toString()
                                                )
                                        }
                                        //......storing image...........
                                        storage =
                                            FirebaseStorage.getInstance().reference.child("images/pic.jpg")
                                                .child(
                                                    key
                                                ).child(name.text.toString())
                                        deleteimgae(key,Name)
                                        storeimage(key,Name)
                                        checker = -1
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

    private fun deleteimgae(key: String, name: Editable?) {
            storage_deletion = FirebaseStorage.getInstance().reference.child("images/pic.jpg")
            storage_deletion.child(key).child(name.toString())
        if(image_added == 0) {
            storage.delete().addOnCompleteListener(OnCompleteListener {
                    Log.i("image : "," deleted")
            }).addOnFailureListener(OnFailureListener {
                Log.i("image : "," fail to delete")
            })
        }
    }

    private fun storeimage(key: String, Name: Editable) {
        imageUri?.let {
            storage.putFile(it).addOnSuccessListener {
                image_added = 1
                Toast.makeText(activity, "Product stored successfully ", Toast.LENGTH_SHORT).show()
                storage.downloadUrl.addOnSuccessListener {
                    Log.i("message : "," "+it)
                    var k = "" as String
                    var r = "" as String
                    r = it.toString()
                    Log.i("Toekn : "," "+r)
                    var data: DatabaseReference
                    data = FirebaseDatabase.getInstance().getReference("Products")
                    data.child(key).child(Name.toString()).child("Token").setValue(r)
                }
                checker = -1
                progress.visibility = View.INVISIBLE
                    Log.i("image upload : ", "Successfull")
            }
                    .addOnFailureListener(){
                        Toast.makeText(
                            activity,
                            "Some thing went wrong!! please try again later",
                            Toast.LENGTH_SHORT
                        ).show()
                        checker = -1
                        progress.visibility = View.INVISIBLE
                        Log.i("image upload : ", "Fail")
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
