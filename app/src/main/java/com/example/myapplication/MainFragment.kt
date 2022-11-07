package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity

class MainFragment : FragmentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val questionBtn = findViewById<Button>(R.id.questionBtn)
        val alarmBtn = findViewById<Button>(R.id.alarmBtn)
        val lockScreenSwitch = findViewById<Switch>(R.id.lockScreenSwitch)

       questionBtn.setOnClickListener {
            startActivity(Intent(this,QuestionFragment::class.java))
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

        return view
    }
}
