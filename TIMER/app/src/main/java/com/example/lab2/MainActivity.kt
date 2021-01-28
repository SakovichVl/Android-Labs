package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2.App.Companion.instance

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.mainToolbar))
        set_Adapter()
        findViewById<View>(R.id.settingsButton).setOnClickListener { i: View -> onClick(i) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        recreate()
    }

    fun onCreateButtonClick(view: View?) {
        val intent = Intent(applicationContext, CreateActivity::class.java)
        intent.putExtra("timerId", intArrayOf(0, 0)).flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun set_Adapter() {
        val db = instance!!.db
        val listView = findViewById<RecyclerView>(R.id.timersList)
        listView.adapter = TimerAdapter(this, R.layout.item, db!!.tDAO()!!.allTimers, db)
        listView.layoutManager = LinearLayoutManager(this)
    }

    private fun onClick(i: View) {
        startActivityForResult(Intent(this, Settings::class.java), 1)
    }
}