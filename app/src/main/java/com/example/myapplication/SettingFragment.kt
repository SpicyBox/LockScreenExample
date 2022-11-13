package com.example.myapplication

import android.Manifest
import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class SettingFragment : Fragment() {

    val user = Firebase.auth.currentUser
    val db = Firebase.firestore
    val storage = Firebase.storage
    val storageRef = storage.reference

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
            }
        }
    }
    private val seletProfile = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri == null) {
            Toast.makeText(getActivity(), "사진 선택 취소", Toast.LENGTH_SHORT).show()
        } else {
            var file = Uri.fromFile(File(uri.toString()))
            val riversRef = storageRef.child("userProfile/${file.lastPathSegment}")
            val uploadTask = riversRef.putFile(file)

            uploadTask.addOnFailureListener {
                Log.w(TAG,"업로드 실패${uri.toString()}")
            }.addOnSuccessListener { taskSnapshot ->
                Log.d(TAG,"업로드 성공${uri.toString()}")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_setting, container, false)
        val userEmailTxt = view.findViewById<TextView>(R.id.userEmailTxt)
        val userNameTxt = view.findViewById<TextView>(R.id.userNameTxt)
        val profileImg = view.findViewById<ImageView>(R.id.profileImg)

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

        profileImg.setOnClickListener {
            checkPermission.launch(permissionList)
            seletProfile.launch("image/*")
        }

        return view
    }
}