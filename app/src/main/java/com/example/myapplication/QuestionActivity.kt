package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.room.Room

class QuestionActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val englishWordBtn = findViewById<Button>(R.id.englishWordBtn)
        val toeic = findViewById<Button>(R.id.toeicBtn)

        englishWordBtn.setOnClickListener{
            startActivity(Intent(this,QuestionPlayActivity::class.java))
        }
    }
}