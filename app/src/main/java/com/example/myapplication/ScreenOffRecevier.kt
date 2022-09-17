package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ScreenOffRecevier : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent?.action) {
            Intent.ACTION_SCREEN_OFF -> {
                val intent = Intent(context, LockActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                context?.startActivity(intent)
            }
        }
}