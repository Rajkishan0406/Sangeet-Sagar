package com.example.sangeetsagarowner.NavigationMenuDrawer

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.sangeetsagarowner.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import java.nio.BufferUnderflowException
import java.text.SimpleDateFormat
import java.util.*

class Time_Update : BottomSheetDialogFragment() {

    var sta  = 0 as Int
    var time_from = "" as String
    var time_to = "" as String
    lateinit var btn : CardView
    lateinit var op :CardView
    lateinit var cl : CardView
    lateinit var status : TextView
    lateinit var set_time_from : CardView
    lateinit var set_time_to : CardView
    lateinit var from_time : TextView
    lateinit var to_time : TextView
    lateinit var bun : Bundle

    lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.time_update, container, false)

        bun = Bundle()
        bun = this.requireArguments()
        var name : String? = bun.getString("day")

        btn = view.findViewById(R.id.update_time_done)
        op = view.findViewById(R.id.open_cardview)
        cl = view.findViewById(R.id.close_cardview)
        status = view.findViewById(R.id.status)
        set_time_from = view.findViewById(R.id.set_time_from)
        set_time_to = view.findViewById(R.id.set_time_to)
        from_time = view.findViewById(R.id.time_text_from)
        to_time = view.findViewById(R.id.time_text_to)

        databaseReference = name?.let { FirebaseDatabase.getInstance().getReference("TimeTable").child(it) }!!

        set_time_to.setOnClickListener(View.OnClickListener {
            var cal = Calendar.getInstance()
            var timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker,hour,minute ->
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE, minute)

                var time = SimpleDateFormat("HH:mm").format(cal.time)
                var hour = time.substring(0,2)
                if(hour.compareTo("12") < 0) {
                    time = time + " AM"
                }
                else
                {
                    var jj = hour.toInt()
                    jj = jj - 12
                    time = jj.toString() + time.subSequence(2,time.length) + " PM"
                }
                to_time.setText(time)
                time_from = to_time.text.toString()
            }
            TimePickerDialog(activity,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false).show()
        })

        set_time_from.setOnClickListener(View.OnClickListener {
            var Cal = Calendar.getInstance()
            var timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker,hour,minute ->
                Cal.set(Calendar.HOUR_OF_DAY,hour)
                Cal.set(Calendar.MINUTE, minute)

                var Time = SimpleDateFormat("HH:mm").format(Cal.time)
                var Hour = Time.substring(0,2)
                if(Hour.compareTo("12") < 0) {
                    Time = Time + " AM"
                }
                else{
                    var jj = Hour.toInt()
                    jj = jj - 12
                    Time = jj.toString() + Time.subSequence(2,Time.length) + " PM"
                }
                from_time.setText(Time)
                time_to = from_time.text.toString()
            }
            TimePickerDialog(activity,timeSetListener,Cal.get(Calendar.HOUR_OF_DAY),Cal.get(Calendar.MINUTE),false).show()
        })

        op.setOnClickListener(View.OnClickListener {
            sta = 1
            status.setText("Open")
        })

        cl.setOnClickListener(View.OnClickListener {
            sta = -1
            status.setText("Close")
        })

        btn.setOnClickListener(View.OnClickListener {
            if(sta == 0 || time_from.isEmpty() || time_to.isEmpty() || sta == -1) {
                if(sta == 0)
                Toast.makeText(activity, "Please fill all details", Toast.LENGTH_SHORT).show()
                else if(sta == -1){
                    databaseReference.child("from").setValue("------")
                    databaseReference.child("to").setValue("------")
                    databaseReference.child("status").setValue(status.text.toString())
                    Toast.makeText(activity,"Time Table Updated successfully",Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                else
                    Toast.makeText(activity, "Please fill all details", Toast.LENGTH_SHORT).show()
            }
            else{
                databaseReference.child("from").setValue(time_from)
                databaseReference.child("to").setValue(time_to)
                databaseReference.child("status").setValue(status.text.toString())
                Toast.makeText(activity,"Time Table Updated successfully",Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })

        return view
    }

}