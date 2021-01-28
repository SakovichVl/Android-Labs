package com.example.lab2

import android.app.Application
import androidx.room.Room
import com.example.lab2.DB.AppDatabase

class App : Application() {
    var db: AppDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(this, AppDatabase::class.java, "db1")
                .allowMainThreadQueries()
                .build()
    }

    companion object {
        @JvmStatic
        var instance: App? = null
    }
}