package com.example.sangeetsagarowner.NavigationMenuDrawer

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.sangeetsagarowner.R
import com.google.firebase.database.*
import org.w3c.dom.Text

class TimetableFragment :Fragment(){

    lateinit var fb : CardView
    lateinit var newitem : Time_Update
    lateinit var from_text : TextView
    lateinit var to_text : TextView
    lateinit var status : TextView
    lateinit var status_card : CardView
    lateinit var progress : ProgressBar
    lateinit var open : LottieAnimationView
    lateinit var close : LottieAnimationView


    lateinit var sun_card : CardView
    lateinit var mon_card : CardView
    lateinit var tues_card : CardView
    lateinit var wed_card : CardView
    lateinit var thurs_card : CardView
    lateinit var fri_card : CardView
    lateinit var sat_card : CardView
    lateinit var sun_text : TextView
    lateinit var mon_text : TextView
    lateinit var tues_text : TextView
    lateinit var wed_text : TextView
    lateinit var thurs_text : TextView
    lateinit var fri_text : TextView
    lateinit var sat_text : TextView

    lateinit var frame : FrameLayout

    lateinit var database : DatabaseReference
    var present = "sunday" as String

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.timetable_fragment,container,false)

        frame = view.findViewById(R.id.time_table_owner)

        val animation = AnimationUtils.loadAnimation(activity, R.anim.fragment_transaction)
        frame.startAnimation(animation)

        from_text = view.findViewById(R.id.time_from)
        to_text = view.findViewById(R.id.to_from)
        status = view.findViewById(R.id.status)
        status_card = view.findViewById(R.id.open_cardview)
        progress = view.findViewById(R.id.progress_bar_time)
        open = view.findViewById(R.id.open_animation)
        close = view.findViewById(R.id.closed_animation)

        //all cardview declaration
        sun_card = view.findViewById(R.id.sunday)
        mon_card = view.findViewById(R.id.monday)
        tues_card = view.findViewById(R.id.tuesday)
        wed_card = view.findViewById(R.id.wednesday)
        thurs_card = view.findViewById(R.id.thursday)
        fri_card = view.findViewById(R.id.friday)
        sat_card = view.findViewById(R.id.saturday)

        //alltextview declaration
        sun_text = view.findViewById(R.id.text_sunday)
        mon_text = view.findViewById(R.id.text_monday)
        tues_text = view.findViewById(R.id.text_tuesday)
        wed_text = view.findViewById(R.id.text_wednesday)
        thurs_text = view.findViewById(R.id.text_thursday)
        fri_text = view.findViewById(R.id.text_friday)
        sat_text = view.findViewById(R.id.text_saturday)

        sun_card.setCardBackgroundColor(Color.LTGRAY)
        sun_text.setTextColor(Color.BLUE)
        mon_text.setTextColor(Color.MAGENTA)
        tues_text.setTextColor(Color.MAGENTA)
        wed_text.setTextColor(Color.MAGENTA)
        thurs_text.setTextColor(Color.MAGENTA)
        fri_text.setTextColor(Color.MAGENTA)
        sat_text.setTextColor(Color.MAGENTA)
        setData("sunday")

        //click action on card
        sun_card.setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            sun_card.setCardBackgroundColor(Color.LTGRAY)
            sun_text.setTextColor(Color.BLUE)
            mon_text.setTextColor(Color.MAGENTA)
            tues_text.setTextColor(Color.MAGENTA)
            wed_text.setTextColor(Color.MAGENTA)
            thurs_text.setTextColor(Color.MAGENTA)
            fri_text.setTextColor(Color.MAGENTA)
            sat_text.setTextColor(Color.MAGENTA)
            mon_card.setCardBackgroundColor(Color.WHITE)
            tues_card.setCardBackgroundColor(Color.WHITE)
            wed_card.setCardBackgroundColor(Color.WHITE)
            thurs_card.setCardBackgroundColor(Color.WHITE)
            fri_card.setCardBackgroundColor(Color.WHITE)
            sat_card.setCardBackgroundColor(Color.WHITE)
            present = "sunday"
            setData("sunday")
        })
        mon_card.setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            mon_card.setCardBackgroundColor(Color.LTGRAY)
            mon_text.setTextColor(Color.BLUE)
            sun_text.setTextColor(Color.MAGENTA)
            tues_text.setTextColor(Color.MAGENTA)
            wed_text.setTextColor(Color.MAGENTA)
            thurs_text.setTextColor(Color.MAGENTA)
            fri_text.setTextColor(Color.MAGENTA)
            sat_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            sun_card.setCardBackgroundColor(Color.WHITE)
            tues_card.setCardBackgroundColor(Color.WHITE)
            wed_card.setCardBackgroundColor(Color.WHITE)
            thurs_card.setCardBackgroundColor(Color.WHITE)
            fri_card.setCardBackgroundColor(Color.WHITE)
            sat_card.setCardBackgroundColor(Color.WHITE)
            present = "monday"
            setData("monday")
        })
        tues_card.setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            tues_card.setCardBackgroundColor(Color.LTGRAY)
            tues_text.setTextColor(Color.BLUE)
            sun_text.setTextColor(Color.MAGENTA)
            mon_text.setTextColor(Color.MAGENTA)
            wed_text.setTextColor(Color.MAGENTA)
            thurs_text.setTextColor(Color.MAGENTA)
            fri_text.setTextColor(Color.MAGENTA)
            sat_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            sun_card.setCardBackgroundColor(Color.WHITE)
            mon_card.setCardBackgroundColor(Color.WHITE)
            wed_card.setCardBackgroundColor(Color.WHITE)
            thurs_card.setCardBackgroundColor(Color.WHITE)
            fri_card.setCardBackgroundColor(Color.WHITE)
            sat_card.setCardBackgroundColor(Color.WHITE)
            present = "tuesday"
            setData("tuesday")
        })
        wed_card.setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            wed_card.setCardBackgroundColor(Color.LTGRAY)
            wed_text.setTextColor(Color.BLUE)
            mon_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            tues_text.setTextColor(Color.MAGENTA)
            thurs_text.setTextColor(Color.MAGENTA)
            fri_text.setTextColor(Color.MAGENTA)
            sat_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            tues_card.setCardBackgroundColor(Color.WHITE)
            sun_card.setCardBackgroundColor(Color.WHITE)
            mon_card.setCardBackgroundColor(Color.WHITE)
            thurs_card.setCardBackgroundColor(Color.WHITE)
            fri_card.setCardBackgroundColor(Color.WHITE)
            sat_card.setCardBackgroundColor(Color.WHITE)
            present = "wednesday"
            setData("wednesday")
        })
        thurs_card.setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            thurs_card.setCardBackgroundColor(Color.LTGRAY)
            thurs_text.setTextColor(Color.BLUE)
            mon_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            tues_text.setTextColor(Color.MAGENTA)
            wed_text.setTextColor(Color.MAGENTA)
            fri_text.setTextColor(Color.MAGENTA)
            sat_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            tues_card.setCardBackgroundColor(Color.WHITE)
            sun_card.setCardBackgroundColor(Color.WHITE)
            mon_card.setCardBackgroundColor(Color.WHITE)
            wed_card.setCardBackgroundColor(Color.WHITE)
            fri_card.setCardBackgroundColor(Color.WHITE)
            sat_card.setCardBackgroundColor(Color.WHITE)
            present = "thursday"
            setData("thursday")
        })
        fri_card.setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            fri_card.setCardBackgroundColor(Color.LTGRAY)
            fri_text.setTextColor(Color.BLUE)
            mon_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            tues_text.setTextColor(Color.MAGENTA)
            wed_text.setTextColor(Color.MAGENTA)
            thurs_text.setTextColor(Color.MAGENTA)
            sat_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            tues_card.setCardBackgroundColor(Color.WHITE)
            sun_card.setCardBackgroundColor(Color.WHITE)
            mon_card.setCardBackgroundColor(Color.WHITE)
            wed_card.setCardBackgroundColor(Color.WHITE)
            thurs_card.setCardBackgroundColor(Color.WHITE)
            sat_card.setCardBackgroundColor(Color.WHITE)
            present = "friday"
            setData("friday")
        })
        sat_card.setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            sat_card.setCardBackgroundColor(Color.LTGRAY)
            sat_text.setTextColor(Color.BLUE)
            mon_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            tues_text.setTextColor(Color.MAGENTA)
            wed_text.setTextColor(Color.MAGENTA)
            thurs_text.setTextColor(Color.MAGENTA)
            fri_text.setTextColor(Color.MAGENTA)
            sun_text.setTextColor(Color.MAGENTA)
            tues_card.setCardBackgroundColor(Color.WHITE)
            sun_card.setCardBackgroundColor(Color.WHITE)
            mon_card.setCardBackgroundColor(Color.WHITE)
            wed_card.setCardBackgroundColor(Color.WHITE)
            thurs_card.setCardBackgroundColor(Color.WHITE)
            fri_card.setCardBackgroundColor(Color.WHITE)
            present = "saturday"
            setData("saturday")
        })


        fb = view.findViewById(R.id.update_Timing)

        newitem = Time_Update()


        fb.setOnClickListener(View.OnClickListener {
            var bun : Bundle
            bun = Bundle()
            bun.putString("day",present)
            newitem.arguments = bun
            newitem.show(childFragmentManager,"bottom sheet time updation")
        })

        return view
    }

    private fun setData(day : String) {
            var databaseReference : DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("TimeTable")
        databaseReference.child(day).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    from_text.setText(snapshot.child("from").getValue().toString())
                    to_text.setText(snapshot.child("to").getValue().toString())
                    var ss = snapshot.child("status").getValue().toString()
                    if(ss.equals("Close"))
                    {
                           status_card.setCardBackgroundColor(Color.RED)
                           status.setText("Close")
                           close.visibility = View.VISIBLE
                           open.visibility = View.INVISIBLE
                    }
                    else{
                        status_card.setCardBackgroundColor(Color.GREEN)
                        status.setText("Open")
                        open.visibility = View.VISIBLE
                        close.visibility = View.INVISIBLE
                    }
                    progress.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,""+error,Toast.LENGTH_SHORT).show();
            }

        })
    }
}