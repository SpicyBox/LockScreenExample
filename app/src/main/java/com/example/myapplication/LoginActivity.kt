package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditTxt = findViewById<EditText>(R.id.emailEditTxt)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditTxt)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val singUpBtn = findViewById<Button>(R.id.singUpBtn)

        val auth = FirebaseAuth.getInstance()

        if(Firebase.auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }

        loginBtn.setOnClickListener{

            auth.signInWithEmailAndPassword(emailEditTxt.text.toString(),passwordEditText.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,MainActivity::class.java))
                   } else {

                        Toast.makeText(this, "이메일 혹은 패스워드가 다릅니다", Toast.LENGTH_LONG).show()
                    }
                }
        }

        singUpBtn.setOnClickListener {
            startActivity(Intent(this,SingUpActivity::class.java))
        }
    }
}