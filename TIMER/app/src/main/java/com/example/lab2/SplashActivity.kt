package com.example.lab2

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startInit()
        splashScreen()
    }

    private fun startInit() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val configuration = Configuration()
        val locale = Locale(preferences.getString("test_lang", "ru"))
        Locale.setDefault(locale)
        configuration.locale = locale
        configuration.fontScale = preferences.getString("font_size", "1.0")!!.toFloat()
        baseContext.resources.updateConfiguration(configuration, null)
        if (preferences.getBoolean("theme", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun splashScreen() {
        setContentView(R.layout.activity_splash)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val timeSplash: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    finish()
                    startActivity(Intent(baseContext, MainActivity::class.java))
                }
            }
        }
        timeSplash.start()
    }
}