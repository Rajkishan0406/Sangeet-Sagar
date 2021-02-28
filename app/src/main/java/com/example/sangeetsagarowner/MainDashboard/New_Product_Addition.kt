package com.example.sangeetsagarowner.MainDashboard

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
    var check = -1
    lateinit var image : ImageView
    lateinit var select : ImageView
    private var imageUri: Uri? = null
    private val pickImage = 100
    lateinit var bun : Bundle

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
        Des = view.findViewById(R.id.About_Model)
        Ava = view.findViewById(R.id.available_cardview)
        UnAva = view.findViewById(R.id.unavailable_cardview)
        submit = view.findViewById(R.id.done)
        image = view.findViewById(R.id.image)
        select = view.findViewById(R.id.select_image)

        select.setOnClickListener(View.OnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        })


        database = FirebaseDatabase.getInstance().getReference("Products")

        Ava.setOnClickListener(View.OnClickListener {
            check = 1;
        })

        UnAva.setOnClickListener(View.OnClickListener {
            check = 0;
        })


        bun = Bundle()
        bun = this.requireArguments()
        var key : String? = bun.getString("item")
        Toast.makeText(activity,"Item Name : "+key,Toast.LENGTH_SHORT).show()

        submit.setOnClickListener(View.OnClickListener {
            var Name = name.text
            var Brand = brand.text
            var Price = price.text
            var Weight = wright.text
            var Power = power.text
            var Describe = Des.text
            if(Name.isEmpty() || Brand.isEmpty() || Price.isEmpty() || Weight.isEmpty() || Power.isEmpty() || Describe.isEmpty()
                    || check == -1 || imageUri == null)
            {
                Toast.makeText(activity,"Please fill all information ",Toast.LENGTH_SHORT).show()
            }
            else{

            }
        })

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            image.setImageURI(imageUri)
        }

    }
}