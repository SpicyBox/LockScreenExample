package com.example.myapplication

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val user = Firebase.auth.currentUser

        user?.let {
            for (profile in it.providerData) {
                val photoUrl = profile.photoUrl
                if(photoUrl == null){
                    val profileUpdates = userProfileChangeRequest {
                        displayName = "defultProfile"
                        photoUri = Uri.parse("gs://englishcoach-7f95b.appspot.com/defult_profile.png")
                    }

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                            }
                        }

                }
            }
        }

        return view
    }
}
