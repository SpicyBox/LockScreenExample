package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val questionBtn = view.findViewById<Button>(R.id.questionBtn)
        val alarmBtn = view.findViewById<Button>(R.id.alarmBtn)
        val lockScreenSwitch = view.findViewById<Switch>(R.id.lockScreenSwitch)
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        user?.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo Url
                val name = profile.displayName
                val email = profile.email
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

       questionBtn.setOnClickListener {
            //startActivity(Intent(getActivity(),QuestionFragment::class.java))
        }

        alarmBtn.setOnClickListener {
            getActivity()?.stopService(Intent(getActivity(), LockScreenService::class.java))
        }

        lockScreenSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                getActivity()?.startForegroundService(Intent(getActivity(), LockScreenService::class.java))
            } else {
                getActivity()?.stopService(Intent(getActivity(), LockScreenService::class.java))
            }
        }

        return view
    }
}
