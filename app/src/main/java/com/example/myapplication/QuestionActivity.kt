package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.myapplication.Model.User

class QuestionActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        var db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "userDB"
        ).build()

        val englishWordBtn = findViewById<Button>(R.id.englishWordBtn)
        val toeic = findViewById<Button>(R.id.toeicBtn)

        englishWordBtn.setOnClickListener{
            startActivity(Intent(this,QuestionPlayActivity::class.java))
        }
        
    }

}