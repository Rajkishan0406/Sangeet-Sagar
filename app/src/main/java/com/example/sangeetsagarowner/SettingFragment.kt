package com.example.sangeetsagarowner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.airbnb.lottie.LottieAnimationView
import com.example.sangeetsagarowner.Authentication.AuthenticationActivity
import com.example.sangeetsagarowner.MainDashboard.About_Owner
import com.example.sangeetsagarowner.MainDashboard.Brand_Name
import com.example.sangeetsagarowner.NavigationMenuDrawer.Contact_Developer
import com.example.sangeetsagarowner.NavigationMenuDrawer.Sold_Product_Addition
import com.example.sangeetsagarowner.NavigationMenuDrawer.TimetableFragment
import com.google.firebase.auth.FirebaseAuth

class SettingFragment : Fragment() {

    lateinit var owner : CardView
    lateinit var time : CardView
    lateinit var ask : CardView
    lateinit var brand : CardView
    lateinit var logout : CardView
    lateinit var sold_pro : CardView
    lateinit var mAuth : FirebaseAuth
    lateinit var lot : LottieAnimationView

    lateinit var frame : FrameLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.setting_fragment, container, false)

        owner = view.findViewById(R.id.owner)
        time = view.findViewById(R.id.time)
        ask = view.findViewById(R.id.ask_developer)
        sold_pro = view.findViewById(R.id.sold_product)
        brand = view.findViewById(R.id.brand_collection)
        logout = view.findViewById(R.id.logout)
        lot = view.findViewById(R.id.lottie_setting)

        frame = view.findViewById(R.id.setting_owner)

        val animationx = AnimationUtils.loadAnimation(activity, R.anim.fragment_transaction)
        frame.startAnimation(animationx)

        //Animation
        val animation = AnimationUtils.loadAnimation(activity, R.anim.trans_r_l)
        lot.startAnimation(animation)

        mAuth = FirebaseAuth.getInstance()

        sold_pro.setOnClickListener(View.OnClickListener {
            setsoldPro(Sold_Product_Addition())
        })

        owner.setOnClickListener(View.OnClickListener {
            setownerSetting(About_Owner())
        })

        time.setOnClickListener(View.OnClickListener {
            setTimeTable(TimetableFragment())
        })

        ask.setOnClickListener(View.OnClickListener {
            setask(Contact_Developer())
        })

        brand.setOnClickListener(View.OnClickListener {
            setbrandFragment(Brand_Name())
        })

        logout.setOnClickListener(View.OnClickListener {
            val intent = Intent(getActivity(), AuthenticationActivity::class.java)
            mAuth.signOut()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        })

        return view;
    }

    private fun setTimeTable(forgotFragment: TimetableFragment) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }
    private fun setask(forgotFragment: Contact_Developer) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }
    private fun setbrandFragment(forgotFragment: Brand_Name) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

    private fun setownerSetting(forgotFragment: About_Owner) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

    private fun setsoldPro(forgotFragment: Sold_Product_Addition) {
        var ft: FragmentTransaction? = getFragmentManager()?.beginTransaction()
        if (ft != null) {
            ft.replace(R.id.dashboard_frame, forgotFragment)
        }
        if (ft != null) {
            ft.addToBackStack(null).commit()
        }
    }

    }