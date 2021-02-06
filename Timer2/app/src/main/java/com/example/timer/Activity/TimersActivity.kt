package com.example.timer.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.RecyclerView.TimerInTimerPageAdapter
import com.example.timer.Service.TimeService
import com.example.timer.Settings.Settings.installSettings
import com.example.timer.ViewModel.TimersPageViewModel

class TimersActivity : AppCompatActivity() {
    private var mViewModel: TimersPageViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        installSettings(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timers)
        mViewModel = ViewModelProvider(this).get(TimersPageViewModel::class.java)
        mViewModel!!.setSequence(intent.getLongExtra("sequence_id", 0))
        val layout = findViewById<View>(R.id.timer_page_constraint_layout) as ConstraintLayout
        layout.setBackgroundColor(mViewModel!!.colorSequence)
        val text = findViewById<TextView>(R.id.timer_page_sequence_name_textView)
        text.setText(mViewModel!!.nameSequence)
        val recyclerView = findViewById<RecyclerView>(R.id.timer_page_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TimerInTimerPageAdapter(mViewModel!!.timers)
        val time = findViewById<TextView>(R.id.timer_page_time_text_view)
        mViewModel!!.timer.observe(this, { s -> time.text = s })
        val name = findViewById<TextView>(R.id.timer_page_name_text_view)
        mViewModel!!.getName().observe(this, { s -> name.text = s })
        val intent = Intent(applicationContext, TimeService::class.java)
        intent.putExtra("Sequence", mViewModel!!.sequence)
        var btn = findViewById<Button>(R.id.start_timer_button)
        btn.setOnClickListener {
            intent.putExtra("Type", "Start")
            startService(intent)
        }
        btn = findViewById(R.id.stop_timer_button)
        btn.setOnClickListener {
            intent.putExtra("Type", "Stop")
            startService(intent)
        }
        btn = findViewById(R.id.next_timer_button)
        btn.setOnClickListener {
            intent.putExtra("Type", "Next")
            startService(intent)
        }
        btn = findViewById(R.id.prev_timer_button)
        btn.setOnClickListener {
            intent.putExtra("Type", "Prev")
            startService(intent)
        }
        val br: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val time = intent.getLongExtra("Time", 0)
                val name = intent.getStringExtra("Name")
                mViewModel!!.setTime(time.toString())
                mViewModel!!.setName(name!!)
            }
        }
        val intentFilter = IntentFilter("Broadcast")
        registerReceiver(br, intentFilter)
    }
}