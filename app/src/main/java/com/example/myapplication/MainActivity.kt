package com.example.myapplication

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Binder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import com.example.myapplication.databinding.ActivityLockBinding
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionBtn = findViewById<Button>(R.id.questionBtn)
        val alarmBtn = findViewById<Button>(R.id.alarmBtn)
        val lockScreenSwitch = findViewById<Switch>(R.id.lockScreenSwitch)

       questionBtn.setOnClickListener {
            startActivity(Intent(this,QuestionActivity::class.java))
        }

        alarmBtn.setOnClickListener {
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
