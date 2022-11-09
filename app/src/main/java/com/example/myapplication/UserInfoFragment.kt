package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_user_info, container, false)
        val userEmailTxt = view.findViewById<TextView>(R.id.userEmailTxt)

        val user = Firebase.auth.currentUser
        user?.let {
            userEmailTxt.text = user.email.toString()
        }

        return view
    }
}