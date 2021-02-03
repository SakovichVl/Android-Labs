package com.example.timer.Database

import android.content.ContentValues
import android.content.Context
import com.example.timer.Model.Sequence
import com.example.timer.Model.Timer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.String
import java.util.*

object Database {
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
        db.update(DatabaseHelper.TIMER_TABLE, cv, "id = " + timer.id.toString(), null)
    }

    fun InsertSequence(context: Context, sequence: Sequence) {
        val db =
            context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.SEQUENCE_TABLE +
                    " (id INTEGER PRIMARY KEY, " + DatabaseHelper.NAME_SEQUENCE_ROW + " TEXT, " + DatabaseHelper.COLOR_SEQUENCE_ROW +
                    " INTEGER, " + DatabaseHelper.TIMERS_SEQUENCE_ROW + " TEXT);"
        )
        val cv = ContentValues()
        cv.put(DatabaseHelper.NAME_SEQUENCE_ROW, sequence.name)
        cv.put(DatabaseHelper.COLOR_SEQUENCE_ROW, sequence.color)
        db.insert(DatabaseHelper.SEQUENCE_TABLE, null, cv)
    }

    fun getSequences(context: Context): ArrayList<Sequence> {
        val db =
            context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.SEQUENCE_TABLE +
                    " (id INTEGER PRIMARY KEY, " + DatabaseHelper.NAME_SEQUENCE_ROW + " TEXT, " + DatabaseHelper.COLOR_SEQUENCE_ROW +
                    " INTEGER, " + DatabaseHelper.TIMERS_SEQUENCE_ROW + " TEXT);"
        )
        val cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.SEQUENCE_TABLE + ";", null)
        val sequences = ArrayList<Sequence>()
        val builder = GsonBuilder()
        val gson = builder.create()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            val color = cursor.getInt(2)
            val timers = gson.fromJson<ArrayList<Timer>>(
                cursor.getString(3),
                object : TypeToken<ArrayList<Timer?>?>() {}.type
            )
            sequences.add(Sequence(id, name, color, timers))
        }
        return sequences
    }

    fun deleteSequence(context: Context, id: Long) {
        val db =
            context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
        db.delete(DatabaseHelper.SEQUENCE_TABLE, "id = ?", arrayOf(id.toString()))
    }

    fun UpdateSequence(context: Context, sequence: Sequence) {
        val db =
            context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
        val builder = GsonBuilder()
        val gson = builder.create()
        val cv = ContentValues()
        cv.put(DatabaseHelper.NAME_SEQUENCE_ROW, sequence.name)
        cv.put(DatabaseHelper.COLOR_SEQUENCE_ROW, sequence.color)
        cv.put(DatabaseHelper.TIMERS_SEQUENCE_ROW, gson.toJson(sequence.timers))
        db.update(DatabaseHelper.SEQUENCE_TABLE, cv, "id = " + String.valueOf(sequence.id), null)
    }

    fun getSequence(context: Context, id: Long): Sequence {
        val db =
            context.openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
        val cursor = db.rawQuery(
            "SELECT * FROM " + DatabaseHelper.SEQUENCE_TABLE + " WHERE id = " + id + ";",
            null
        )
        var sequence = Sequence()
        val builder = GsonBuilder()
        val gson = builder.create()
        if (cursor.moveToNext()) {
            val name = cursor.getString(1)
            val color = cursor.getInt(2)
            val timers = gson.fromJson<ArrayList<Timer>>(
                cursor.getString(3),
                object : TypeToken<ArrayList<Timer?>?>() {}.type
            )
            sequence = Sequence(id, name, color, timers)
        }
        return sequence
    }
}