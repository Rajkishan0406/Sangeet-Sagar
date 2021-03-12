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
import org.w3c.dom.Text
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

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.time_update, container, false)

        btn = view.findViewById(R.id.update_time_done)
        op = view.findViewById(R.id.open_cardview)
        cl = view.findViewById(R.id.close_cardview)
        status = view.findViewById(R.id.status)
        set_time_from = view.findViewById(R.id.set_time_from)
        set_time_to = view.findViewById(R.id.set_time_to)
        from_time = view.findViewById(R.id.time_text_from)
        to_time = view.findViewById(R.id.time_text_to)

        set_time_to.setOnClickListener(View.OnClickListener {
            var cal = Calendar.getInstance()
            var timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker,hour,minute ->
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE, minute)

                var time = SimpleDateFormat("HH:mm").format(cal.time)
                var hour = time.substring(0,2)
                if(hour.compareTo("12") > 0) {
                    Toast.makeText(activity,"more than 12",Toast.LENGTH_SHORT).show()
                }

                to_time.setText(time)
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
                if(Hour.compareTo("12") > 0) {
                    Toast.makeText(activity,"more than 12",Toast.LENGTH_SHORT).show()
                }
                from_time.setText(Time)
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
            if(sta == 0 || from_time.toString().isEmpty() || to_time.toString().isEmpty())
                Toast.makeText(activity,"Please fill all details",Toast.LENGTH_SHORT).show()
            else{
                Toast.makeText(activity,"All details are filled",Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

}