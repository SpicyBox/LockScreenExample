package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myapplication.Model.User
import java.util.Random

class QuestionPlayActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_play)

        var db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "userDB"
        ).build()

        val qeustionText = findViewById<TextView>(R.id.qeustionText)
        val timerTxt = findViewById<TextView>(R.id.timeTxt)
        val timeProgressBar = findViewById<ProgressBar>(R.id.timeProgressBar)
        val resultEnterBtn = findViewById<Button>(R.id.resultEnterBtn)
        val answerEnterEdit = findViewById<EditText>(R.id.answerEnterEdit)

        var questionList = listOf("Cat", "Dog", "Book", "Bus", "Phone", "Mail", "Key", "Cap", "Chair", "Table")
        var answerList = listOf("cat", "dog", "book", "bus", "phone", "mail", "key", "cap", "chair", "table")
        var num = Random().nextInt(9)
        var scoreCount = 0

        qeustionText.text = questionList[num]

        fun startQuestionPlayActivity(): Unit {
            startActivity(Intent(this,ResultActivity::class.java).putExtra("scoreCount", scoreCount))
            finish()
        }

        resultEnterBtn.setOnClickListener{
            if (answerEnterEdit.text.toString() == answerList[num]){
                Toast.makeText(this, "정답입니다.", Toast.LENGTH_SHORT).show()
                scoreCount++
                num = Random().nextInt(9)
                qeustionText.text = questionList[num]
            } else {
                Toast.makeText(this, "오답입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        object : CountDownTimer(1000 * 30, 1000) {
            override fun onTick(p0: Long) {
                // countDownInterval 마다 호출 (여기선 1000ms)
                timerTxt.text = (p0 / 1000).toString()
                timeProgressBar.setProgress(p0.toInt())

            }

            override fun onFinish() {
                // 타이머가 종료되면 호출
                val r = Runnable {
                    val userDAO = db.userDao()
                    val userScore: List<User> = userDAO.getAll()
                    if (userScore[0].highScore!! < scoreCount) {
                        val updateSocre = userDAO.updateHighScore(User(0, scoreCount))
                    }
                }

                val thread = Thread(r)
                thread.start()

                startQuestionPlayActivity()
            }
        }.start()
    }
}