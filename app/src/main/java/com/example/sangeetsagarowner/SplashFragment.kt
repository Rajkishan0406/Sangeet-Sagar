package com.example.sangeetsagarowner

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.sangeetsagarowner.Authentication.AuthenticationActivity
import com.example.sangeetsagarowner.Authentication.ForgotPassword
import com.example.sangeetsagarowner.Authentication.LoginFragment
import com.example.sangeetsagarowner.MainDashboard.Dashboard
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    lateinit var logo: ImageView
    lateinit var text: TextView
    lateinit var auth : FirebaseAuth

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
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.splashfragment, container, false)

        logo = view.findViewById(R.id.logo_front)
        text = view.findViewById(R.id.text_front)
        auth = FirebaseAuth.getInstance()


        logo.animate().apply {
            duration = 2000
            rotationXBy(360f)
            rotationYBy(360f)
        }.start()


        val animation = AnimationUtils.loadAnimation(activity, R.anim.fadein)
        text.startAnimation(animation)

        Handler().postDelayed({
            val currentUser = auth.currentUser
            if(currentUser != null){
                val intent = Intent(getActivity(), Dashboard::class.java)
                getActivity()?.startActivity(intent)
            }
            else {
                setFragment(LoginFragment())
            }
        }, 3000)

        return view
    }

    private fun setFragment(forgotFragment: LoginFragment) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.Auth_Frame, forgotFragment)
        }
        if (ft != null) {
            ft.commit()
        }

    }
}