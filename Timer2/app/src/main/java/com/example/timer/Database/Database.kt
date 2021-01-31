package com.example.timer.Database

import android.content.ContentValues
import android.content.Context
import com.example.timer.Model.Timer
import java.lang.String
import java.util.*

class Database {
    companion object{
        fun InsertTimer(timer: Timer, context: Context) {
            val db =
                context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TIMER_TABLE +
                        " (id INTEGER PRIMARY KEY, " + DatabaseHelper.NAME_TIMER_ROW + " TEXT, " + DatabaseHelper.DURATION_TIMER_ROW +
                        " INTEGER);"
            )
            val cv = ContentValues()
            cv.put(DatabaseHelper.NAME_TIMER_ROW, timer.name)
            cv.put(DatabaseHelper.DURATION_TIMER_ROW, timer.duration)
            db.insert(DatabaseHelper.TIMER_TABLE, null, cv)
        }

        fun getTimers(context: Context): ArrayList<Timer> {
            val db =
                context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
            val cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TIMER_TABLE + ";", null)
            val timers = ArrayList<Timer>()
            while (cursor.moveToNext()) {
                val id = cursor.getLong(0)
                val name = cursor.getString(1)
                val duration = cursor.getInt(2)
                timers.add(Timer(id, name, duration))
            }
            cursor.close()
            return timers
        }

        fun deleteTimer(context: Context, id: Long) {
            val db =
                context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
            db.delete(DatabaseHelper.TIMER_TABLE, "id = ?", arrayOf(id.toString()))
        }

        fun UpdateTimer(context: Context, timer: Timer) {
            val db =
                context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
            val cv = ContentValues()
            cv.put(DatabaseHelper.NAME_TIMER_ROW, timer.name)
            cv.put(DatabaseHelper.DURATION_TIMER_ROW, timer.duration)
            db.update(DatabaseHelper.TIMER_TABLE, cv, "id = " + String.valueOf(timer.id), null)
        }
    }
}