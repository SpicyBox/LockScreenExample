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
                                            "questionType" to 0,
                                            "setLimitTime" to 30,
                                            "setrepeatNum" to 1,
                                            "lifeTypeHighScore" to 0,
                                            "toeicTypeHighScore" to 0,
                                            "journalTypeHighScore" to 0
                                        )
                                        db.collection("userInfo").document(user.uid)
                                            .set(userInfo)
                                            .addOnSuccessListener {
                                                Toast.makeText(this, "???????????? ??????", Toast.LENGTH_LONG).show()
                                                AuthUI.getInstance()
                                                    .signOut(this)
                                                    .addOnCompleteListener {
                                                        startActivity(Intent(this,LoginActivity::class.java))
                                                    } }
                                            .addOnFailureListener { e -> Log.w(TAG, "?????????!!!", e) }

                                    }

                                } else {
                                    Log.e(TAG, "?????????!")
                                }
                            }
                    }
                    else if (task.exception?.message.isNullOrEmpty()==false){
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"?????? ?????? ??????????????????.",Toast.LENGTH_LONG).show()
                    }
                }

        }
    }
}