package com.example.myapplication

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_start = findViewById<Button>(R.id.btn_start)
        val btn_stop = findViewById<Button>(R.id.btn_stop)

        btn_start.setOnClickListener {
            this.startForegroundService(Intent(this, LockScreenService::class.java)
        }

        btn_stop.setOnClickListener {
            this.stopService(Intent(this, LockScreenService::class.java)
        }
    }
}
