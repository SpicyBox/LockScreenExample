package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SingUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

        val emailEditTxt = findViewById<EditText>(R.id.emailEditTxt)
        val passwordEditTxt = findViewById<EditText>(R.id.passwordEditTxt)
        val singUpBtn = findViewById<Button>(R.id.singUpBtn)

        val auth = FirebaseAuth.getInstance()

        singUpBtn.setOnClickListener{
            auth?.createUserWithEmailAndPassword(emailEditTxt.text.toString(),passwordEditTxt.text.toString())
                ?.addOnCompleteListener {  //통신 완료가 된 후 무슨일을 할지
                        task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,LoginActivity::class.java))
                    }
                    else if (task.exception?.message.isNullOrEmpty()==false){
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"이미 있는 이메일입니다.",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}