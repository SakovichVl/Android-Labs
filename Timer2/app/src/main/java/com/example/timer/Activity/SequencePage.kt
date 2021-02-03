package com.example.timer.Activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.RecyclerView.AllTimersAdapter
import com.example.timer.RecyclerView.ContainsTimerSequenceAdapter
import com.example.timer.ViewModel.SequencePageViewModel

class SequencePage : AppCompatActivity() {
    private var mViewModel: SequencePageViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sequence_page)
        mViewModel = ViewModelProvider(this).get(SequencePageViewModel::class.java)
        mViewModel!!.setSequence(intent.getLongExtra("sequence_id", 0))
        val text = findViewById<TextView>(R.id.name_sequence_page_text_view)
        text.setText(mViewModel!!.sequenceName)
        val layout = findViewById<View>(R.id.layout_sequence_page) as ConstraintLayout
        layout.setBackgroundColor(mViewModel!!.sequenceColor)
        val allTimersRecyclerView = findViewById<RecyclerView>(R.id.timer_sequence_recycler_view)
        allTimersRecyclerView.layoutManager = LinearLayoutManager(this)
        allTimersRecyclerView.adapter = AllTimersAdapter(mViewModel!!.timers, mViewModel!!.timer!!)
        val containsTimerRecyclerView =
            findViewById<RecyclerView>(R.id.timer_contains_sequence_recycler_view)
        containsTimerRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter =
            ContainsTimerSequenceAdapter(mViewModel!!.timersInSequence!!, mViewModel!!.sequence!!)
        containsTimerRecyclerView.adapter = adapter
        mViewModel!!.timer!!.observe(this, { timer ->
            mViewModel!!.InsertTimerInSequence(timer!!)
            adapter.notifyDataSetChanged()
        })
    }
}