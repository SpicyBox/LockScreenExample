package com.example.myapplication

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val db = Firebase.firestore
        val storage = Firebase.storage
        val storageRef = storage.reference

        val lifeTypeFirstImg = view.findViewById<ImageView>(R.id.lifeTypeFirstImg)
        val lifeTypeFirstNickNameTxt = view.findViewById<TextView>(R.id.lifeTypeFirstNickNameTxt)
        val lifeTypeFirstScoreTxt = view.findViewById<TextView>(R.id.lifeTypeFirstScoreTxt)
        val lifeTypeSecondImg = view.findViewById<ImageView>(R.id.lifeTypeSecondImg)
        val lifeTypeSecondNickNameTxt = view.findViewById<TextView>(R.id.lifeTypeSecondNickNameTxt)
        val lifeTypeSecondScoreTxt = view.findViewById<TextView>(R.id.lifeTypeSecondScoreTxt)
        val lifeTypeThirdImg = view.findViewById<ImageView>(R.id.lifeTypeThirdImg)
        val lifeTypeThirdNickNameTxt = view.findViewById<TextView>(R.id.lifeTypeThirdNickNameTxt)
        val lifeTypeThirdScoreTxt = view.findViewById<TextView>(R.id.lifeTypeThirdScoreTxt)
        val toeicTypeFirstImg = view.findViewById<ImageView>(R.id.toeicTypeFirstImg)
        val toeicTypeFirstNickNameTxt = view.findViewById<TextView>(R.id.toeicTypeFirstNickNameTxt)
        val toeicTypeFirstScoreTxt = view.findViewById<TextView>(R.id.toeicTypeFirstScoreTxt)
        val toeicTypeSecondImg = view.findViewById<ImageView>(R.id.toeicTypeSecondImg)
        val toeicTypeSecondNickNameTxt = view.findViewById<TextView>(R.id.toeicTypeSecondNickNameTxt)
        val toeicTypeSecondScoreTxt = view.findViewById<TextView>(R.id.toeicTypeSecondScoreTxt)
        val toeicTypeThirdImg = view.findViewById<ImageView>(R.id.toeicTypeThirdImg)
        val toeicTypeThirdNickNameTxt = view.findViewById<TextView>(R.id.toeicTypeThirdNickNameTxt)
        val toeicTypeThirdScoreTxt = view.findViewById<TextView>(R.id.toeicTypeThirdScoreTxt)
        val journalTypeFirstImg = view.findViewById<ImageView>(R.id.journalTypeFirstImg)
        val journalTypeFirstNickNameTxt = view.findViewById<TextView>(R.id.journalTypeFirstNickNameTxt)
        val journalTypeFirstScoreTxt = view.findViewById<TextView>(R.id.journalTypeFirstScoreTxt)
        val journalTypeSecondImg = view.findViewById<ImageView>(R.id.journalTypeSecondImg)
        val journalTypeSecondNickNameTxt = view.findViewById<TextView>(R.id.journalTypeSecondNickNameTxt)
        val journalTypeSecondScoreTxt = view.findViewById<TextView>(R.id.journalTypeSecondScoreTxt)
        val journalTypeThirdImg = view.findViewById<ImageView>(R.id.journalTypeThirdImg)
        val journalTypeThirdNickNameTxt = view.findViewById<TextView>(R.id.journalTypeThirdNickNameTxt)
        val journalTypeThirdScoreTxt = view.findViewById<TextView>(R.id.journalTypeThirdScoreTxt)

        fun setRankerImg(userUID: String, ImgView: ImageView){
            storageRef.child("userProfile/${userUID}").downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri) // 불러올 이미지 url
                    .into(ImgView) // 이미지를 넣을 뷰
            }.addOnFailureListener {
                Log.e(TAG, "이미지 불러오기 실패")
            }
        }

        fun getRankerInfo(questionType: String){
            db.collection("rank").document(questionType)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        when(questionType){
                            "lifeType" -> {
                                setRankerImg(document.get("firstRankUID").toString(), lifeTypeFirstImg)
                                lifeTypeFirstNickNameTxt.text = document.get("firstRankNickName").toString()
                                lifeTypeFirstScoreTxt.text = document.get("firstRankScore").toString()
                                setRankerImg(document.get("secondRankUID").toString(), lifeTypeSecondImg)
                                lifeTypeSecondNickNameTxt.text = document.get("secondRankNickName").toString()
                                lifeTypeSecondScoreTxt.text = document.get("secondRankScore").toString()
                                setRankerImg(document.get("thirdRankUID").toString(), lifeTypeThirdImg)
                                lifeTypeThirdNickNameTxt.text = document.get("thirdRankNickName").toString()
                                lifeTypeThirdScoreTxt.text = document.get("thirdRankScore").toString()
                            }
                            "toeicType" -> {
                                setRankerImg(document.get("firstRankUID").toString(), toeicTypeFirstImg)
                                toeicTypeFirstNickNameTxt.text = document.get("firstRankNickName").toString()
                                toeicTypeFirstScoreTxt.text = document.get("firstRankScore").toString()
                                setRankerImg(document.get("secondRankUID").toString(), toeicTypeSecondImg)
                                toeicTypeSecondNickNameTxt.text = document.get("secondRankNickName").toString()
                                toeicTypeSecondScoreTxt.text = document.get("secondRankScore").toString()
                                setRankerImg(document.get("thirdRankUID").toString(), toeicTypeThirdImg)
                                toeicTypeThirdNickNameTxt.text = document.get("thirdRankNickName").toString()
                                toeicTypeThirdScoreTxt.text = document.get("thirdRankScore").toString()
                            }
                            "journalType" -> {
                                setRankerImg(document.get("firstRankUID").toString(), journalTypeFirstImg)
                                journalTypeFirstNickNameTxt.text = document.get("firstRankNickName").toString()
                                journalTypeFirstScoreTxt.text = document.get("firstRankScore").toString()
                                setRankerImg(document.get("secondRankUID").toString(), journalTypeSecondImg)
                                journalTypeSecondNickNameTxt.text = document.get("secondRankNickName").toString()
                                journalTypeSecondScoreTxt.text = document.get("secondRankScore").toString()
                                setRankerImg(document.get("thirdRankUID").toString(), journalTypeThirdImg)
                                journalTypeThirdNickNameTxt.text = document.get("thirdRankNickName").toString()
                                journalTypeThirdScoreTxt.text = document.get("thirdRankScore").toString()
                            }
                        }
                    }
            }
        }

        getRankerInfo("lifeType")
        getRankerInfo("toeicType")
        getRankerInfo("journalType")

        return view
    }
}
