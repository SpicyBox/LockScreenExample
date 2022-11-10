package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_user_info, container, false)
        val userEmailTxt = view.findViewById<TextView>(R.id.userEmailTxt)
        val nickNameTxt = view.findViewById<TextView>(R.id.nickNameEditTxt)

        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        user?.let {
                db.collection("userInfo").document(user.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            userEmailTxt.text = document.get("email").toString()

                        } else {
                            Toast.makeText(getActivity(), "실패2", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        return view
    }
}