package com.example.myapplication

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

class LockActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                finish()
                true
            }
            else -> super.onTouchEvent(event)
        }
    }
}