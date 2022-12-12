package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuestionFragment : Fragment() {

    val user = Firebase.auth.currentUser
    val db = Firebase.firestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_question, container, false)

        val englishWordBtn = view.findViewById<Button>(R.id.englishWordBtn)
        val englishMeanBtn = view.findViewById<Button>(R.id.englishMeanBtn)
        val englishAlpabetBtn = view.findViewById<Button>(R.id.englishAlpabetBtn)
        val lifeTypeMaxScoreTxt = view.findViewById<TextView>(R.id.lifeTypeMaxScoreTxt)
        val toeicTypeMaxScoreTxt = view.findViewById<TextView>(R.id.toeicTypeMaxScoreTxt)
        val journalTypeMaxScoreTxt = view.findViewById<TextView>(R.id.journalTypeMaxScoreTxt)

        user?.let {
            db.collection("userInfo").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        lifeTypeMaxScoreTxt.text = "최고기록 : ${document.get("lifeTypeHighScore").toString()}점"
                        toeicTypeMaxScoreTxt.text = "최고기록 : ${document.get("toeicTypeHighScore").toString()}점"
                        journalTypeMaxScoreTxt.text = "최고기록 : ${document.get("journalTypeHighScore").toString()}점"
                    }
                }
        }

        englishWordBtn.setOnClickListener{
            startActivity(Intent(getActivity(),QuestionPlayActivity::class.java).putExtra("qeutionType","lifeType"))
        }

        englishMeanBtn.setOnClickListener{
            startActivity(Intent(getActivity(),QuestionPlayActivity::class.java).putExtra("qeutionType","toeicType"))
        }

        englishAlpabetBtn.setOnClickListener{
            startActivity(Intent(getActivity(),QuestionPlayActivity::class.java).putExtra("qeutionType","journalType"))
        }

        return view
    }
}