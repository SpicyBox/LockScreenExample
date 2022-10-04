package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.room.Dao
import androidx.room.Room
import com.example.myapplication.Model.User

class QuestionActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        var db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "userDB"
        ).build()

        var wordMaxScoreTxt = findViewById<TextView>(R.id.wordMaxScoreTxt)

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

        val englishWordBtn = findViewById<Button>(R.id.englishWordBtn)
        val toeicBtn = findViewById<Button>(R.id.toeicBtn)

        englishWordBtn.setOnClickListener{
            startActivity(Intent(this,QuestionPlayActivity::class.java))
        }
    }

}