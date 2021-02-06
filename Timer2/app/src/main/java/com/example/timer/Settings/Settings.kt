package com.example.timer.Settings

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.R

object Settings {
        fun installSettings(activity: AppCompatActivity) {
            val sharedPreferences = activity.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            val idTheme = sharedPreferences.getInt("Theme", R.style.AppTheme)
            activity.setTheme(idTheme)
            val res = activity.resources
            val config = Configuration(res.configuration)
            config.fontScale = sharedPreferences.getFloat("Size_Font", 1f)
            res.configuration.updateFrom(config)
        }
}