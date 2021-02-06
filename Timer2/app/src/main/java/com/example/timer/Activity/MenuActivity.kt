package com.example.timer.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.R
import com.example.timer.Settings.Settings

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Settings.installSettings(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        var btn = findViewById<Button>(R.id.edit_button)
        btn.setOnClickListener {
            val intent = Intent(applicationContext, EditActivity::class.java)
            startActivity(intent)
        }
        btn = findViewById(R.id.home_button)
        btn.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
        btn = findViewById(R.id.setting_button)
        btn.setOnClickListener {
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}