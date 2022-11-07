package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myapplication.Model.User

class ResultActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "userDB"
        ).build()

        val score = intent.getIntExtra("scoreCount", 0)

        val reStartBtn = findViewById<Button>(R.id.reStartBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)
        val resultTxt = findViewById<TextView>(R.id.resultTxt)
        val titleTxt = findViewById<TextView>(R.id.titleTxt)

        resultTxt.text = score.toString() + " 점"

        val r = Runnable {
            val userDAO = db.userDao()
            val userScore: List<User> = userDAO.getAll()
            if (userScore[0].highScore!! < score) {
                titleTxt.text = "최고 기록 갱신!"
                val updateSocre = userDAO.updateHighScore(User(0, score))
            }
        }

        val thread = Thread(r)
        thread.start()

        reStartBtn.setOnClickListener{
            startActivity(Intent(this,QuestionPlayActivity::class.java))
        }

        backBtn.setOnClickListener{
            startActivity(Intent(this, QuestionFragment::class.java))
        }
    }
}