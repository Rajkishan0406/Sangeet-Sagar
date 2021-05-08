package com.example.sangeetsagarowner.Authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.Customer.CustomerActivity
import com.example.sangeetsagarowner.MainDashboard.Dashboard
import com.example.sangeetsagarowner.R
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(){

    lateinit var forgot : TextView
    lateinit var pass : EditText
    lateinit var email: EditText
    lateinit var btn : CardView
    lateinit var mAuth : FirebaseAuth
    lateinit var progress : ProgressBar
    lateinit var customer : CardView

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if(currentUser != null){
            val intent = Intent(getActivity(), Dashboard::class.java)
            getActivity()?.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
            var view:View = inflater.inflate(R.layout.loginfragment, container, false)


        mAuth = FirebaseAuth.getInstance()

        forgot = view.findViewById(R.id.forgot_password_textview)
        pass = view.findViewById(R.id.email)
        email = view.findViewById(R.id.password)
        btn = view.findViewById(R.id.cardview_button)
        progress = view.findViewById(R.id.login_progress_bar)
        customer = view.findViewById(R.id.customer)

        customer.setOnClickListener(View.OnClickListener {
            val intent = Intent(getActivity(), CustomerActivity::class.java)
            getActivity()?.startActivity(intent)
        })

        btn.setOnClickListener(View.OnClickListener {
            var E = email.text.toString();
            var P = pass.text.toString();
            if (E.isEmpty()) {
                Toast.makeText(activity, "Please enter email id", Toast.LENGTH_SHORT).show()
            }
            if (P.isEmpty()) {
                Toast.makeText(activity, "Please enter password", Toast.LENGTH_SHORT).show()
            }
            progress.visibility = View.VISIBLE
            login(E, P)
        })



        forgot.setOnClickListener(View.OnClickListener {
            setFragment(ForgotPassword())
        })


        return view;
    }

    private fun login(p: String, e: String) {
        activity?.let {
            mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        progress.visibility = View.INVISIBLE
                        Toast.makeText(activity, "Sign in Successfull", Toast.LENGTH_SHORT).show()
                        val intent = Intent(getActivity(), Dashboard::class.java)
                        getActivity()?.startActivity(intent)
                    } else {
                        progress.visibility = View.INVISIBLE
                        Toast.makeText(activity, "Wrong Password or some Server Failure Please try again later" ,
                                Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun setFragment(forgotFragment: ForgotPassword) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.Auth_Frame, forgotFragment)
        }
        if (ft != null) {
            ft.commit()
        }
    }


}

