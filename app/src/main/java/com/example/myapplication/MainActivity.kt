package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_start = findViewById<Button>(R.id.btn_start)
        val btn_stop = findViewById<Button>(R.id.btn_end)
        val lockScreenSwitch = findViewById<Switch>(R.id.lockScreenSwitch)

        btn_start.setOnClickListener {
            this.startForegroundService(Intent(this, LockScreenService::class.java))
        }

        btn_stop.setOnClickListener {
            this.stopService(Intent(this, LockScreenService::class.java))
        }

        lockScreenSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                this.startForegroundService(Intent(this, LockScreenService::class.java))
            } else {
                this.stopService(Intent(this, LockScreenService::class.java))
            }
        }
    }
}
