package com.example.myapplication.DAO

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.Model.Question

@Dao
interface QuestionDAO {
    @Query("SELECT * FROM Question")
    fun getAll(): List<Question>

    @Query("DELETE FROM Question")
    fun deleteAll()
}