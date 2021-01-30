package com.example.timer.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.R

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val btn = findViewById<Button>(R.id.edit_button)
        btn.setOnClickListener {
            val intent = Intent(applicationContext, EditActivity::class.java)
            startActivity(intent)
        }
    }
}