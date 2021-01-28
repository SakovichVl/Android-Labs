package com.example.lab2.DB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TimerModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tDAO(): TimerDao?
}