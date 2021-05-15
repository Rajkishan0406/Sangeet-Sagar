package com.example.sangeetsagarowner.Authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sangeetsagarowner.MainDashboard.Dashboard
import com.example.sangeetsagarowner.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : Fragment(){

    lateinit var mAuth : FirebaseAuth
    lateinit var email : EditText
    lateinit var btn : CardView
    lateinit var progress : ProgressBar
    lateinit var frame : FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view:View =  inflater.inflate(R.layout.forgotpassword,container,false)

        frame = view.findViewById(R.id.forgot_frame)
        val animation = AnimationUtils.loadAnimation(activity, R.anim.fragment_transaction)
        frame.startAnimation(animation)

        mAuth = FirebaseAuth.getInstance()
        email = view.findViewById(R.id.email)
        btn = view.findViewById(R.id.cardview_forgot_password_button)
        progress = view.findViewById(R.id.cardview_forgot_password_progressbar)

        btn.setOnClickListener(View.OnClickListener {
           var E = email.text.toString()
            if(E.isEmpty())
                Toast.makeText(activity,"Please enter your email",Toast.LENGTH_SHORT).show()
            else {
                progress.visibility = View.VISIBLE
                forgotpassword(E)
            }
        })

        return view;
    }

    private fun forgotpassword(e: String) {
        activity?.let {
            mAuth.sendPasswordResetEmail(e)
                    .addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            progress.visibility = View.INVISIBLE
                            Toast.makeText(activity,"Password reset link send to the registered email",Toast.LENGTH_SHORT).show()
                        } else {
                            progress.visibility = View.INVISIBLE
                            Toast.makeText(activity, "Wrong Email id please try again",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

}

