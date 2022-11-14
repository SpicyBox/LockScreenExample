package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SingUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

        val emailEditTxt = findViewById<EditText>(R.id.emailEditTxt)
        val passwordEditTxt = findViewById<EditText>(R.id.passwordEditTxt)
        val nickName = findViewById<EditText>(R.id.nickNameEditTxt)
        val singUpBtn = findViewById<Button>(R.id.singUpBtn)

        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        singUpBtn.setOnClickListener{
            auth?.createUserWithEmailAndPassword(emailEditTxt.text.toString(),passwordEditTxt.text.toString())
                ?.addOnCompleteListener {
                        task ->
                    if(task.isSuccessful){

                        auth.signInWithEmailAndPassword(emailEditTxt.text.toString(),passwordEditTxt.text.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = Firebase.auth.currentUser
                                    user?.let {
                                        val userInfo = hashMapOf(
                                            "uid" to user.uid,
                                            "nickName" to nickName.text.toString(),
                                            "email" to emailEditTxt.text.toString(),
                                            "password" to passwordEditTxt.text.toString(),
                                        )
                                        db.collection("userInfo").document(user.uid)
                                            .set(userInfo)
                                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                            .addOnFailureListener { e -> Log.w(TAG, "오류남!!!", e) }
                                    }

                                } else {
                                    Toast.makeText(this, "이메일 혹은 패스워드가 다릅니다", Toast.LENGTH_LONG).show()
                                }
                                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_LONG).show()
                                AuthUI.getInstance()
                                    .signOut(this)
                                    .addOnCompleteListener {
                                    }
                            }
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