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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class QuestionPlayActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_play)

        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        val qeustionText = findViewById<TextView>(R.id.qeustionText)
        val timerTxt = findViewById<TextView>(R.id.timeTxt)
        val timeProgressBar = findViewById<ProgressBar>(R.id.timeProgressBar)
        val resultEnterBtn = findViewById<Button>(R.id.resultEnterBtn)
        val answerEnterEditText = findViewById<EditText>(R.id.answerEnterEditText)
        val scoreCountTxt = findViewById<TextView>(R.id.scoreCountTxt)

        val qeutionType = intent.getStringExtra("qeutionType")
        var questionList = listOf("0")
        var answerList = listOf("0")

        if(qeutionType.equals("lifeType")){
            questionList = listOf("Cat", "Dog", "Book", "Bus", "Phone", "Mail", "Key", "Cap", "Chair", "Table","Cat","Time")//문제 리스트 임시
            answerList = listOf("cat", "dog", "book", "bus", "phone", "mail", "key", "cap", "chair", "table","cat","time") //정답 리스트(한글 안됨)
        } else if(qeutionType.equals("toeicType")){
            questionList = listOf("cat", "dog", "book", "bus", "phone", "mail", "key", "cap", "chair", "table","cat","time")//문제 리스트 임시
            answerList = listOf("Cat", "Dog", "Book", "Bus", "Phone", "Mail", "Key", "Cap", "Chair", "Table","Cat","Time") //정답 리스트(한글 안됨)
        } else {
            questionList = listOf("cat", "dog", "book", "bus", "phone", "mail", "key", "cap", "chair", "table","cat","time")//문제 리스트 임시
            answerList = listOf("Cat", "Dog", "Book", "Bus", "Phone", "Mail", "Key", "Cap", "Chair", "Table","Cat","Time") //정답 리스트(한글 안됨)
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
                    .putExtra("qeutionType", qeutionType)
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
                user?.let {
                    val userInfo = hashMapOf(
                        qeutionType to scoreCount
                    )
                    db.collection("userInfo").document(user.uid)
                        .set(userInfo, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d(
                                TAG,
                                "DocumentSnapshot successfully written!"
                            )
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "오류남!!!", e) }

                    startResultActivity()
                }
            }
        }.start()
    }


}