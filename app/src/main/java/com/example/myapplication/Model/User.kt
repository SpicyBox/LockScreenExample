package com.example.myapplication.Model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class User(
    @ColumnInfo val highScore: String?
)