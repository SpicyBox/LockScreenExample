package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.myapplication.Model.User

class QuestionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_question, container, false)
        var db = Room.databaseBuilder(
            requireContext().applicationContext,
            UserDatabase::class.java,
            "userDB"
        ).build()

        var wordMaxScoreTxt = view.findViewById<TextView>(R.id.wordMaxScoreTxt)

        val r = Runnable {
            val userDAO = db.userDao()
            var userScore: List<User> = userDAO.getAll()
            if (userScore.size == 0) {
                wordMaxScoreTxt.text = "최고점수 : 0"
            } else {
                wordMaxScoreTxt.text = "최고점수 : " + userScore[0].highScore.toString()
            }
        }

        val thread = Thread(r)
        thread.start()

        val englishWordBtn = view.findViewById<Button>(R.id.englishWordBtn)
        val toeicBtn = view.findViewById<Button>(R.id.toeicBtn)

        englishWordBtn.setOnClickListener{
            startActivity(Intent(getActivity(),QuestionPlayActivity::class.java))
        }

        return view
    }
}