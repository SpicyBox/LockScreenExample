package com.example.myapplication.DAO

import androidx.room.*
import com.example.myapplication.Model.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Insert
    fun insertHighScore(user: User)

    @Update
    fun updateHighScore(user: User)

    @Query("DELETE FROM User")
    fun deleteAll()
}

