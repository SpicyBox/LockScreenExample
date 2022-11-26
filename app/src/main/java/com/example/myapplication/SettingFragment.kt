package com.example.myapplication

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.Model.User
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class SettingFragment : Fragment() {

    val userDb = activity?.let { UserDatabase.getDatabase(it) }
    val user = Firebase.auth.currentUser
    val db = Firebase.firestore
    val storage = Firebase.storage
    val storageRef = storage.reference

    var questionType = 0
    var setTime = 20
    var setPsc = 1

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
            }
        }
    }
    private val seletProfile = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri == null) {
            Toast.makeText(activity, "사진 선택 취소", Toast.LENGTH_SHORT).show()
        } else {
            user?.let {
                val riversRef = storageRef.child("userProfile/${user.uid}")
                val uploadTask = riversRef.putFile(uri)

                uploadTask.addOnFailureListener {
                    Log.w(TAG, "업로드 실패${uri.path}")
                }.addOnSuccessListener { taskSnapshot ->
                    Log.d(TAG, "업로드 성공$uri")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_setting, container, false)
        val userEmailTxt = view.findViewById<TextView>(R.id.userEmailTxt)
        val userNameTxt = view.findViewById<TextView>(R.id.userNameTxt)
        val profileImg = view.findViewById<ImageView>(R.id.profileImg)
        val logOutBtn = view.findViewById<Button>(R.id.logOutBtn)
        val setLockScreenPscSpiner = view.findViewById<Spinner>(R.id.setLockScreenPscSpiner)
        val setLockScreenTimeSpnier = view.findViewById<Spinner>(R.id.setLockScreenTimeSpnier)
        val setOptionBtn = view.findViewById<Button>(R.id.setOptionBtn)
        val lockScreenSwitch2 = view.findViewById<Switch>(R.id.lockScreenSwitch2)
        val setQuestionTypeGroup = view.findViewById<RadioGroup>(R.id.setQuestionTypeGroup)

        val r = Runnable {
            var optionList = userDb?.userDao()?.getAll()
            Log.d(TAG, optionList?.get(0)?.questionType.toString())
        }

        val thread = Thread(r)

        if(thread.getState() == Thread.State.NEW) {
            thread.start()
        }

        setLockScreenPscSpiner.adapter =
            activity?.let { ArrayAdapter.createFromResource(it, R.array.question_array, android.R.layout.simple_spinner_item) }

        setLockScreenTimeSpnier.adapter =
            activity?.let { ArrayAdapter.createFromResource(it, R.array.time_array, android.R.layout.simple_spinner_item) }

        val defaultImageUrl = "https://firebasestorage.googleapis.com/v0/b/englishcoach-7f95b.appspot.com/o/defult_profile.png?alt=media&token=ea83c03c-3656-4f81-a9ef-4f206a3daa08"

        user?.let {
                db.collection("userInfo").document(user.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            storageRef.child("userProfile/${user.uid}").downloadUrl.addOnSuccessListener { uri ->
                                // Got the download URL for 'users/me/profile.png'
                            }.addOnFailureListener {
                                // Handle any errors
                            }
                            userEmailTxt.text = document.get("email").toString()
                            userNameTxt.text = document.get("nickName").toString()
                            storageRef.child("userProfile/${user.uid}").downloadUrl.addOnSuccessListener { uri ->
                                Glide.with(this)
                                    .load(uri) // 불러올 이미지 url
                                    .into(profileImg) // 이미지를 넣을 뷰
                            }.addOnFailureListener {
                                Glide.with(this)
                                    .load(defaultImageUrl) // 불러올 이미지 url
                                    .into(profileImg) // 이미지를 넣을 뷰
                            }
                        } else {
                            Toast.makeText(activity, "실패2", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        profileImg.setOnClickListener {
            checkPermission.launch(permissionList)
            seletProfile.launch("image/*")
        }

        setQuestionTypeGroup.setOnCheckedChangeListener{group, checkedId->
            when(checkedId){
                R.id.lifeTypeRadioBtn -> questionType = 0
                R.id.toeicTypeRadioBtn -> questionType = 1
                R.id.journalTypeRadioBtn -> questionType = 2
            }
        }

        setLockScreenPscSpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                when(position){
                    0 -> setPsc = 1
                    1 -> setPsc = 2
                    2 -> setPsc = 3
                    3 -> setPsc = 4
                    4 -> setPsc = 5
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        setLockScreenTimeSpnier.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                when(position){
                    0 -> setTime = 20
                    1 -> setTime = 30
                    2 -> setTime = 40
                    3 -> setTime = 50
                    4 -> setTime = 60
                    5 -> setTime = 70
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        setOptionBtn.setOnClickListener {
            setOption(questionType,setPsc,setTime)
        }

        lockScreenSwitch2.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                activity?.startForegroundService(Intent(activity, LockScreenService::class.java))
            } else {
                activity?.stopService(Intent(activity, LockScreenService::class.java))
            }
        }

        logOutBtn.setOnClickListener {
            logout()
        }

        return view
    }

    fun setOption(questionType: Int, setrepeatNum: Int, setLimitTime: Int){

        val r = Runnable {
            val updateOption = userDb?.userDao()?.updateHighScore(User(0, questionType, setrepeatNum, setLimitTime))
            updateOption
            Log.d(TAG, "${questionType}, ${setrepeatNum}, ${setLimitTime}업로드 작동")
        }

        val thread = Thread(r)
        thread.start()
    }

    fun logout(){
        val builder = AlertDialog.Builder(activity)
        builder
            .setTitle("로그아웃")
            .setMessage("로그아웃하시겠습니까?")
            .setPositiveButton("네",
                DialogInterface.OnClickListener { dialog, id ->
                    activity?.let { it1 ->
                        AuthUI.getInstance()
                            .signOut(it1)
                            .addOnCompleteListener {
                                val logoutDialog = AlertDialog.Builder(activity)
                                logoutDialog
                                    .setTitle("로그아웃")
                                    .setMessage("로그아웃되었습니다.")
                                    .setPositiveButton("확인",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            startActivity(Intent(activity, LoginActivity::class.java))
                                        })
                                logoutDialog.create()
                                logoutDialog.show()
                            }
                    }
                })
            .setNegativeButton("아니오",
                DialogInterface.OnClickListener { dialog, id ->
                    // Cancel 버튼 선택 시 수행
                })
        builder.create()
        builder.show()
    }
}