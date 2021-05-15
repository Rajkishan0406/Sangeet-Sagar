package com.example.sangeetsagarowner.NavigationMenuDrawer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Contact_Developer : Fragment() {

    lateinit var message : EditText
    lateinit var btn : CardView
    lateinit var his : CardView
    lateinit var progress : ProgressBar
    lateinit var reply : CardView

    lateinit var mAuth : FirebaseAuth
    lateinit var database : DatabaseReference
    lateinit var database2 : DatabaseReference

    lateinit var frame : FrameLayout
    var ref = 0 as Int;

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.contact_developer,container,false)

        progress = view.findViewById(R.id.ask_progress)
        his = view.findViewById(R.id.history)
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Message")
        database2 = FirebaseDatabase.getInstance().getReference("Reply")

        frame = view.findViewById(R.id.contact_frame)

        val animation = AnimationUtils.loadAnimation(activity, R.anim.fragment_transaction)
        frame.startAnimation(animation)

        var UserId = mAuth.currentUser?.uid

        reply = view.findViewById(R.id.reply)

        reply.setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            database2.child("Developer").orderByValue().addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var ans = snapshot.getValue() as String
                        progress.visibility = View.INVISIBLE
                        Toast.makeText(activity,""+ans,Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        })

        his.setOnClickListener(View.OnClickListener {
            ref = 1;
            loadData()
        })

        btn = view.findViewById(R.id.send)
        message = view.findViewById(R.id.ask_edittext)

        btn.setOnClickListener(View.OnClickListener {
            var msg  = message.text.toString()
            if(msg.isEmpty()){
                Toast.makeText(activity,"Please enter your message",Toast.LENGTH_SHORT).show()
            }
            else{
                progress.visibility = View.VISIBLE
                if (UserId != null) {
                    progress.visibility = View.INVISIBLE
                    database.child(UserId).setValue(msg)
                    Toast.makeText(activity,"Your Message send to Developer",Toast.LENGTH_SHORT).show()
                }
            }
        })

        return view
    }

    private fun loadData() {
        progress.visibility = View.VISIBLE
        var id = mAuth.currentUser?.uid
        var data : DatabaseReference
        data = id?.let { FirebaseDatabase.getInstance().getReference("Message").child(it) }!!

        data.orderByValue().addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists() && ref == 1){
                    progress.visibility = View.INVISIBLE
                    Toast.makeText(activity,""+snapshot.getValue().toString(),Toast.LENGTH_SHORT).show()
                }
                else{
                    progress.visibility = View.INVISIBLE
                    ref = -1;
                    Log.i("Message : ","No message History")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progress.visibility = View.INVISIBLE
            }

        })
    }
}