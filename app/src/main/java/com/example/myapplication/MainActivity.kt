package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val mainFragment by lazy { MainFragment() }
    private val questionFragment by lazy { QuestionFragment() }
    private val userInfoFragment by lazy { UserInfoFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainNavi = findViewById<BottomNavigationView>(R.id.mainNavi)

        mainNavi.run {
            setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.rank -> {
                        changeFragment(mainFragment)
                    }
                    R.id.question -> {
                        changeFragment(questionFragment)
                    }
                    R.id.info -> {
                        changeFragment(userInfoFragment)
                    }
                    R.id.setting -> {
                        changeFragment(mainFragment)
                    }
                }
                true
            }
            selectedItemId = R.id.rank
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}