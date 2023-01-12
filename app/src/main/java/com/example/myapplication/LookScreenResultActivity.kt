package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.collections.count as count

class LookScreenResultActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra("scoreCount", 0)
        val wrongAnswer = intent.getIntExtra("wrongAnswer", 0)
        val wrongAnswerList = intent.getStringArrayListExtra("wrongAnswerList")
        val wrongQuestionList = intent.getStringArrayListExtra("wrongQuestionList")
        val questionType = intent.getStringExtra("questionType")
        val allAnswer = score + wrongAnswer
        var wrongAnswerTxtString = ""
        var wrongQuestionTxtString = ""

        val reStartBtn = findViewById<Button>(R.id.reStartBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)
        val resultTxt = findViewById<TextView>(R.id.resultTxt)
        val wrongAnswerTxt = findViewById<TextView>(R.id.wrongAnswerTxt)
        val wrongQuestionTxt = findViewById<TextView>(R.id.wrongQuestionTxt)

        resultTxt.text = score.toString() + "/" + allAnswer.toString()

        if (wrongAnswerList != null) {
            for (i: Int in 0..wrongAnswerList.count() - 1){
                wrongAnswerTxtString += wrongAnswerList[i] + "\n"
            }
            wrongAnswerTxt.text = wrongAnswerTxtString
        }

        if (wrongQuestionList != null) {
            for (i: Int in 0..wrongQuestionList.count() - 1){
                wrongQuestionTxtString += wrongQuestionList[i] + "\n"
            }
            wrongQuestionTxt.text = wrongQuestionTxtString
        }

        reStartBtn.setOnClickListener{
            startActivity(Intent(this, LockActivity::class.java).putExtra("qeutionType",questionType))
        }

        backBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}