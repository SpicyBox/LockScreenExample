package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

class QuestionPlayActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_play)

        val qeustionText = findViewById<TextView>(R.id.qeustionText)
        val timerTxt = findViewById<TextView>(R.id.timeTxt)
        val timeProgressBar = findViewById<ProgressBar>(R.id.timeProgressBar)
        val resultEnterBtn = findViewById<Button>(R.id.resultEnterBtn)
        val answerEnterEditText = findViewById<EditText>(R.id.answerEnterEditText)
        val scoreCountTxt = findViewById<TextView>(R.id.scoreCountTxt)

        val qeutionType = intent.getStringExtra("qeutionType")
        var questionList = listOf("0")
        var answerList = listOf("0")
        var wrongQuestionList = ArrayList<String>()
        var wrongAnswerList = ArrayList<String>()

        if(qeutionType.equals("lifeType")){
            questionList = listOf("Cat", "Dog", "Book", "Bus", "Phone", "Mail", "Key", "Cap", "Chair", "Table","Time")
            answerList = listOf("고양이", "개", "책", "버스", "핸드폰", "편지", "열쇠", "모자", "의자", "책상","시간")
        } else if(qeutionType.equals("toeicType")){
            questionList = listOf("Sophistication", "Consecutive", "Quality", "Complete", "Information", "Deliberation", "Convenient", "Increase", "Decrease", "Enhance","Impressive","Reduce")
            answerList = listOf("세련된", "연속적인", "품질", "완료하다", "정보", "토의", "편리한", "증가하다", "줄다", "향상시키다","인상적인","줄이다")
        } else {
            questionList = listOf("Propose", "Investigate", "Address", "Classify", "Employ", "Discover", "Deduce", "Evaluate", "Approximate", "Validate","Approach","Deploy")
            answerList = listOf("주장하다", "연구하다", "쟁점을 다루다", "분류하다", "수행하다", "발견하다", "추론하다", "분석하다", "계산하다", "입증하다","접근법","배치하다")
        }

        var num = Random().nextInt(9)
        var scoreCount = 0
        var wrongAnswer = 0

        qeustionText.text = questionList[num]

        fun startResultActivity(){
            startActivity(
                Intent(this,ResultActivity::class.java)
                    .putExtra("scoreCount", scoreCount)
                    .putExtra("wrongAnswer", wrongAnswer)
                    .putExtra("questionType", qeutionType)
                    .putExtra("wrongQuestionList", wrongQuestionList)
                    .putExtra("wrongAnswerList", wrongAnswerList)
            )
            finish()
        }

        fun playQuestion(answer: List<String>, question: List<String>){
            Log.e(TAG, "실행")
            if (answerEnterEditText.text.toString() == answer[num]){
                Toast.makeText(this, "정답입니다.", Toast.LENGTH_SHORT).show()
                scoreCount++
                scoreCountTxt.text = scoreCount.toString() + "점"
                num = Random().nextInt(9)
                qeustionText.text = question[num]
            } else {
                Toast.makeText(this, "오답입니다.", Toast.LENGTH_SHORT).show()
                wrongQuestionList.add(question[num])
                wrongAnswerList.add(answer[num])
                wrongAnswer++
                num = Random().nextInt(9)
                qeustionText.text = question[num]
            }
            answerEnterEditText.text.clear()
        }

        answerEnterEditText.setOnKeyListener{ v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                playQuestion(answerList, questionList)
            }
            true
        }

        resultEnterBtn.setOnClickListener{
            playQuestion(answerList, questionList)
        }

        object : CountDownTimer(1000 * 60, 1000) {
            override fun onTick(p0: Long) {
                // countDownInterval 마다 호출 (여기선 1000ms)
                timerTxt.text = (p0 / 1000).toString()
                timeProgressBar.setProgress(p0.toInt())
            }

            override fun onFinish() {
                // 타이머가 종료되면 호출
                startResultActivity()
            }
        }.start()
    }


}