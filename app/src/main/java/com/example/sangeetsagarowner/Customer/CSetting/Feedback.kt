package com.example.sangeetsagarowner.Customer.CSetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R

class Feedback :Fragment() {

    lateinit var text : EditText
    lateinit var btn : CardView
    lateinit var pro : ProgressBar

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.feedback, container, false)


        text = view.findViewById(R.id.ask_edittext)
        btn = view.findViewById(R.id.send_feedback)
        pro = view.findViewById(R.id.progress_feedback)

        btn.setOnClickListener(View.OnClickListener {
            var name = text.text.toString()
            if(name.length == 0){
                Toast.makeText(activity,"Please enter your message",Toast.LENGTH_SHORT).show()
            }
            else{
                pro.visibility = View.VISIBLE
                //mail feedback....
                pro.visibility = View.INVISIBLE
            }
        })

        return  view
    }

}