package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class LockActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        val multipleChoiceTxt = findViewById<TextView>(R.id.multipleChoiceTxt)
        val choice1Btn = findViewById<Button>(R.id.choice1Btn)
        val choice2Btn = findViewById<Button>(R.id.choice2Btn)
        val choice3Btn = findViewById<Button>(R.id.choice3Btn)
        val choice4Btn = findViewById<Button>(R.id.choice4Btn)
        val lockTimeTxt = findViewById<TextView>(R.id.lockTimeTxt)
        val lockTimeProgressBar = findViewById<ProgressBar>(R.id.lockTimeProgressBar)

        val questionList = listOf("Cat", "Dog", "Book", "Bus", "Phone", "Mail", "Key", "Cap", "Chair", "Table")
        val answerList = listOf("고양이", "개", "책", "버스", "핸드폰", "우편", "열쇠", "모자", "의자", "책상")
        var buttonList = mutableListOf<Int>()
        var questionNum = 0
        var repeatNum = 1
        var questionType = 0
        var setTime:Long = 20
        var setPsc = 1

        fun finishActivity(){
            ActivityCompat.finishAffinity(this)
        }

        fun setTime(setTime:Long){
            object : CountDownTimer(1000 * setTime, 1000) {
                override fun onTick(p0: Long) {
                    // countDownInterval 마다 호출 (여기선 1000ms)
                    lockTimeTxt.text = (p0 / 1000).toString()
                    lockTimeProgressBar.setProgress(p0.toInt())
                }

                override fun onFinish() {
                    finishActivity()
                }
            }.start()
        }

        user?.let{
            db.collection("userInfo").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        questionType = document.get("questionType").toString().toInt()
                        setTime = document.get("setLimitTime").toString().toLong()
                        setPsc = document.get("setrepeatNum").toString().toInt()
                        Log.d(TAG, setTime.toString())

                        setTime(setTime)
                    } else {
                        //Toast.makeText(getActivity(), "실패2", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        fun setQuestion(){
            var buttonSet = mutableSetOf<Int>()
            while (buttonSet.size < 4){
                var num = Random().nextInt(9)
                buttonSet.add(num)
            }

            buttonList = buttonSet.toMutableList()
            questionNum = buttonList[Random().nextInt(3)]

            multipleChoiceTxt.text = questionList[questionNum].toString()
            choice1Btn.text = answerList[buttonList[0]].toString()
            choice2Btn.text = answerList[buttonList[1]].toString()
            choice3Btn.text = answerList[buttonList[2]].toString()
            choice4Btn.text = answerList[buttonList[3]].toString()
        }

        fun checkAnswer(questionNum: Int, answer: Int){
            if(questionNum == answer){
                Toast.makeText(this, "정답입니다.", Toast.LENGTH_SHORT).show()
                if(repeatNum < setPsc) {
                    setQuestion()
                    repeatNum++
                } else {
                    finishActivity()
                }
            } else{
                Toast.makeText(this, "오답입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        setQuestion()

        choice1Btn.setOnClickListener{
            checkAnswer(buttonList[0], questionNum)
        }

        choice2Btn.setOnClickListener{
            checkAnswer(buttonList[1], questionNum)
        }

        choice3Btn.setOnClickListener{
            checkAnswer(buttonList[2], questionNum)
        }

        choice4Btn.setOnClickListener{
            checkAnswer(buttonList[3], questionNum)
        }
    }
}