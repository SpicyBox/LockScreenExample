package com.example.myapplication.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.Model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Insert
    fun insertHighScore(user: User)

    @Query("DELETE FROM User")
    fun deleteAll()
}