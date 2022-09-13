package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplanModeChangeReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplanModeEnabled = intent?.getBooleanExtra("state", false) ?: return
            if(isAirplanModeEnabled){
                Toast.makeText(context, "Airplane Mode Enable", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Airplane Mode Disabled", Toast.LENGTH_SHORT).show()
            }
    }
}