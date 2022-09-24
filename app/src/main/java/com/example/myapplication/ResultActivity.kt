package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ResultActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val reStartBtn = findViewById<Button>(R.id.reStartBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        reStartBtn.setOnClickListener{
            startActivity(Intent(this,QuestionPlayActivity::class.java))
        }

        backBtn.setOnClickListener{
            startActivity(Intent(this, QuestionActivity::class.java))
        }
    }
}