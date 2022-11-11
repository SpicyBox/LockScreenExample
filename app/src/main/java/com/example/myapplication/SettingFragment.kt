package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_setting, container, false)
        val userEmailTxt = view.findViewById<TextView>(R.id.userEmailTxt)
        val userNameTxt = view.findViewById<TextView>(R.id.userNameTxt)
        val profileImg = view.findViewById<ImageView>(R.id.profileImg)

        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val storage = Firebase.storage

        user?.let {
                db.collection("userInfo").document(user.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            userEmailTxt.text = document.get("email").toString()
                            userNameTxt.text = document.get("nickName").toString()
                            Glide.with(this)
                                .load(document.get("profileUrl").toString()) // 불러올 이미지 url
                                .into(profileImg) // 이미지를 넣을 뷰
                        } else {
                            Toast.makeText(getActivity(), "실패2", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        return view
    }
}