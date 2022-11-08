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
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val questionBtn = view.findViewById<Button>(R.id.questionBtn)
        val alarmBtn = view.findViewById<Button>(R.id.alarmBtn)
        val lockScreenSwitch = view.findViewById<Switch>(R.id.lockScreenSwitch)

       questionBtn.setOnClickListener {
            //startActivity(Intent(getActivity(),QuestionFragment::class.java))
        }

        alarmBtn.setOnClickListener {
            getActivity()?.stopService(Intent(getActivity(), LockScreenService::class.java))
        }

        lockScreenSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                getActivity()?.startForegroundService(Intent(getActivity(), LockScreenService::class.java))
            } else {
                getActivity()?.stopService(Intent(getActivity(), LockScreenService::class.java))
            }
        }

        return view
    }
}
