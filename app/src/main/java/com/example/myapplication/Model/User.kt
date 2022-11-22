package com.example.myapplication.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id : Int?,
    @ColumnInfo val questionType: Int?,
    @ColumnInfo val repeatNum: Int?,
    @ColumnInfo val limitTime: Int?
)