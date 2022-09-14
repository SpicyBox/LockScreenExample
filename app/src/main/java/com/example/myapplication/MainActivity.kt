package com.example.myapplication

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    lateinit var  reciver : AirplanModeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reciver = AirplanModeChangeReceiver()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(reciver,it)
        }

        btn_start.setOnClickListener {
            val intent = Intent(this@MainActivity, MusicPlayerService::class.java)
            intent.action = Actions.START_FOREGROUND
            startService(intent)
        }

        btn_stop.setOnClickListener {
            val intent = Intent(this@MainActivity, MusicPlayerService::class.java)
            intent.action = Actions.STOP_FOREGROUND
            startService(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(reciver)
    }
}