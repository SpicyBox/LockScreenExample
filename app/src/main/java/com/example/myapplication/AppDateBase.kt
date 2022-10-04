package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.DAO.QuestionDAO
import com.example.myapplication.DAO.UserDAO
import com.example.myapplication.Model.Question
import com.example.myapplication.Model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDAO
}

@Database(entities = [Question::class], version = 1)
abstract class QuestionDateBase : RoomDatabase() {
    abstract  fun questionDao() : QuestionDAO
}