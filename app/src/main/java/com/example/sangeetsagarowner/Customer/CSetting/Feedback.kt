package com.example.sangeetsagarowner.Customer.CSetting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.R
import java.lang.Exception

class Feedback :Fragment() {

    lateinit var text : EditText
    lateinit var msg : EditText
    lateinit var btn : CardView
    lateinit var pro : ProgressBar
    lateinit var frame : FrameLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.feedback, container, false)

        frame = view.findViewById(R.id.frame_feedback)
        val animation = AnimationUtils.loadAnimation(activity, R.anim.fragment_transaction)
        frame.startAnimation(animation)

        text = view.findViewById(R.id.ask_edittext)
        btn = view.findViewById(R.id.send_feedback)
        pro = view.findViewById(R.id.progress_feedback)
        msg = view.findViewById(R.id.message_text)

        btn.setOnClickListener(View.OnClickListener {
            var name = text.text.toString()
            var m = msg.text.toString()
            var recipient = "rajkishan101@gmail.com"
            if(name.length == 0 || m.length == 0){
                Toast.makeText(activity,"Please enter your message",Toast.LENGTH_SHORT).show()
            }
            else{
                pro.visibility = View.VISIBLE
                var intent = Intent(Intent.ACTION_SEND)
                intent.data = Uri.parse("mailto")
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                intent.putExtra(Intent.EXTRA_SUBJECT,name)
                intent.putExtra(Intent.EXTRA_TEXT, m)
                intent.setType("message/rfc822")
                try{
                    startActivity(intent)
                    //Intent.createChooser(intent,"Choose Email Client")
                    Toast.makeText(activity,"Mail delievered to developer",Toast.LENGTH_SHORT).show()
                    text.setText("")
                    msg.setText("")
                }
                catch (e: Exception){
                    Toast.makeText(activity,""+e.message.toString(),Toast.LENGTH_SHORT).show()
                }

                pro.visibility = View.INVISIBLE
            }
        })

        return  view
    }

}