package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuestionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_question, container, false)

        var lifeTypeMaxScoreTxt = view.findViewById<TextView>(R.id.wordMaxScoreTxt)

        val englishWordBtn = view.findViewById<Button>(R.id.englishWordBtn)
        val englishMeanBtn = view.findViewById<Button>(R.id.englishMeanBtn)
        val englishAlpabetBtn = view.findViewById<Button>(R.id.englishAlpabetBtn)

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