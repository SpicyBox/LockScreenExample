package com.example.myapplication.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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