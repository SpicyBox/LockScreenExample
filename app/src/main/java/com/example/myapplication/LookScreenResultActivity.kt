package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.count as count

class LookScreenResultActivity:AppCompatActivity() {

    val user = Firebase.auth.currentUser
    val db = Firebase.firestore
    var firstRankUID: String = ""
    var secondRankUID: String = ""
    var thirdRankUID: String = ""
    var firstRankScore: Int = 0
    var secondRankScore: Int = 0
    var thirdRankScore: Int = 0
    var userNickName: String = ""

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

        questionType?.let {
            getHighScore(score, it)
            getRankInfo(score, it)
        }

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
            startActivity(Intent(this, QuestionPlayActivity::class.java).putExtra("qeutionType",questionType))
        }

        backBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun getUserInfo(userUID: String){
        db.collection("userInfo").document(userUID)
            .get()
            .addOnSuccessListener { document ->
                if (document != null){
                    userNickName = document.get("nickName").toString()
                }
            }
    }

    fun getHighScore(score:Int, questionType: String){
        user?.let {
            db.collection("userInfo").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        if (document.get("${questionType}HighScore").toString().toInt() < score
                            || document.get("${questionType}HighScore").toString().toInt() == null){
                            uploadHighScore(score, questionType)
                        }
                    }
                }
        }
    }

    fun uploadHighScore(highScore: Int, questionType: String) {
        user?.let {
            val data = hashMapOf(
                 "${questionType}HighScore" to highScore
            )

            db.collection("userInfo").document(user.uid)
                .set(data, com.google.firebase.firestore.SetOptions.merge())
                .addOnSuccessListener {
                    val setDialog = android.app.AlertDialog.Builder(this)
                    setDialog
                        .setTitle("최고기록갱신!")
                        .setPositiveButton("확인",
                            android.content.DialogInterface.OnClickListener { dialog, id ->
                            })
                    setDialog.create()
                    setDialog.show()
                }
                .addOnFailureListener { e ->
                    android.util.Log.w(
                        android.content.ContentValues.TAG,
                        "오류남!!!",
                        e
                    )
                }
        }
    }

    fun getRankInfo(score: Int, questionType: String){
        db.collection("rank").document(questionType)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    firstRankUID = document.get("firstRankUID").toString()
                    secondRankUID = document.get("secondRankUID").toString()
                    thirdRankUID = document.get("thirdRankUID").toString()
                    firstRankScore = document.get("firstRankScore").toString().toInt()
                    secondRankScore = document.get("secondRankScore").toString().toInt()
                    thirdRankScore = document.get("thirdRankScore").toString().toInt()

                    user?.let {
                        if (user.uid.equals(firstRankUID)||user.uid.equals(secondRankUID)||user.uid.equals(thirdRankUID))
                        else{
                            when{
                                firstRankScore < score -> uploadRankScore(score, questionType, "firstRank")
                                secondRankScore < score -> uploadRankScore(score, questionType, "secondRank")
                                thirdRankScore < score -> uploadRankScore(score, questionType, "thirdRank")
                            }
                        }
                    }
                }
            }
    }

    fun uploadRankScore(score: Int, questionType: String, collection:String){
        user?.let{
            getUserInfo(user.uid)
            val data = hashMapOf(
                "${collection}NickName" to userNickName,
                "${collection}Score" to score,
                "${collection}UID" to user.uid
            )
            db.collection("rank").document(questionType)
                .set(data, com.google.firebase.firestore.SetOptions.merge())
                .addOnSuccessListener {
                    val setDialog = android.app.AlertDialog.Builder(this)
                    setDialog
                        .setTitle("랭킹갱신!")
                        .setMessage("축하드립니다 랭킹 순위권 등록에 성공하였습니다.\n 메인화면에서 확인해주세요!")
                        .setPositiveButton("확인",
                            android.content.DialogInterface.OnClickListener { dialog, id ->
                            })
                    setDialog.create()
                    setDialog.show()
                }
                .addOnFailureListener { e ->
                    android.util.Log.w(
                        android.content.ContentValues.TAG,
                        "오류남!!!",
                        e
                    )
                }
        }

    }
}