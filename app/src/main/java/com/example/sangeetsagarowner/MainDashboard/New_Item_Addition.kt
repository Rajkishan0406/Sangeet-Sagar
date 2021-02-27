package com.example.sangeetsagarowner.MainDashboard

import android.graphics.drawable.DrawableContainer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.sangeetsagarowner.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class New_Item_Addition : BottomSheetDialogFragment() {

    lateinit var name :EditText
    lateinit var btn : CardView
    lateinit var database : DatabaseReference

    override fun onResume() {
        super.onResume()

        name.setText("")

    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.new_item_addition,container,false)

        name = view.findViewById(R.id.item_name)
        btn = view.findViewById(R.id.cardview_button_add)
        var id = UUID.randomUUID().toString()
        database = FirebaseDatabase.getInstance().getReference("list")

        btn.setOnClickListener(View.OnClickListener {
            var item  = name.text.toString()
            if(item.length == 0){
                Toast.makeText(activity, "Enter Item!!", Toast.LENGTH_SHORT).show()
            }
            else if(item.length > 20){
                Toast.makeText(activity, "Maximum text length is 20!!", Toast.LENGTH_SHORT).show()
            }
            else{
                database.child(id).setValue(item)
                Toast.makeText(activity, "Item Added", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })


        return view;
    }
}