package com.example.myapplication.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey val qid: Int?,
    @ColumnInfo val question: String?,
    @ColumnInfo val result: String?
)