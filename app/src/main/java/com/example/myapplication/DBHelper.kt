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
            var sql: String = "CREATE TABLE if not exists userDB(" +
                    "id integer primary key autoincrement," +
                    "highScore integer);"
            db.execSQL(sql)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            val sql: String = "DROP TABLE if exists userDB"
            db.execSQL(sql)
            onCreate(db)
        }
    }