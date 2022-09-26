package com.example.myapplication

import android.content.Intent
import android.content.IntentFilter
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class QuestionPlayActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_play)

        val timerTxt = findViewById<TextView>(R.id.timeTxt)
        val timeProgressBar = findViewById<ProgressBar>(R.id.timeProgressBar)
        val resultEnterBtn = findViewById<Button>(R.id.resultEnterBtn)
        val answerEnterEdit = findViewById<EditText>(R.id.answerEnterEdit)
        var scoreCount = 0

        fun startQuestionPlayActivity(): Unit {
            startActivity(Intent(this,ResultActivity::class.java))
            finish()
        }

        resultEnterBtn.setOnClickListener{
            scoreCount++
            Toast.makeText(this, "알림", Toast.LENGTH_SHORT).show()
        }

        object : CountDownTimer(1000 * 6, 1000) {
            override fun onTick(p0: Long) {
                // countDownInterval 마다 호출 (여기선 1000ms)
                timerTxt.text = (p0 / 1000).toString()
                timeProgressBar.setProgress(p0.toInt())
            }

            override fun onFinish() {
                // 타이머가 종료되면 호출
            }
        }.start()
    }

}