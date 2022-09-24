package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
    ): SQLiteOpenHelper(context, name, factory, version){
        override fun onCreate(db: SQLiteDatabase) {
            var sql: String = "CREATE TABLE if not exists highScore(" +
                    "id integer primary key autoincrement," +
                    "userDB text);"
            db.execSQL(sql)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            val sql: String = "DROP TABLE if exists highScore"
            db.execSQL(sql)
            onCreate(db)
        }
    }