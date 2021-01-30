package com.example.timer.Database

import android.content.ContentValues
import android.content.Context
import com.example.timer.Model.Timer

class Database {
    fun addTimer(timer: Timer, context: Context) {
        val db =
            context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TIMER_TABLE +
                    " (id INTEGER PRIMARY KEY, " + DatabaseHelper.NAME_TIMER_ROW + " TEXT, " + DatabaseHelper.DURATION_TIMER_ROW +
                    " INTEGER)"
        )
        val cv = ContentValues()
        cv.put(DatabaseHelper.NAME_TIMER_ROW, timer.name)
        cv.put(DatabaseHelper.DURATION_TIMER_ROW, timer.duration)
        db.insert(DatabaseHelper.TIMER_TABLE, null, cv)
    }
}