package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LockActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        val multipleChoiceTxt = findViewById<TextView>(R.id.multipleChoiceTxt)
        val choice1Btn = findViewById<Button>(R.id.choice1Btn)
        val choice2Btn = findViewById<Button>(R.id.choice2Btn)
        val choice3Btn = findViewById<Button>(R.id.choice3Btn)
        val choice4Btn = findViewById<Button>(R.id.choice4Btn)

        multipleChoiceTxt.text = "Cat"
        choice1Btn.text = "고양이"
        choice2Btn.text = "개"
        choice3Btn.text = "나비"
        choice4Btn.text = "사과"

        choice1Btn.setOnClickListener{
            Toast.makeText(this, "정답입니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        choice2Btn.setOnClickListener{
            Toast.makeText(this, "오답입니다.", Toast.LENGTH_SHORT).show()
        }

        choice3Btn.setOnClickListener{
            Toast.makeText(this, "오답입니다.", Toast.LENGTH_SHORT).show()
        }

        choice4Btn.setOnClickListener{
            Toast.makeText(this, "오답입니다.", Toast.LENGTH_SHORT).show()
        }
    }
}