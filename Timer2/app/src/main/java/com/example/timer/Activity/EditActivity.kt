package com.example.timer.Activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.RecyclerView.TimerAdapter
import com.example.timer.Settings.Settings
import com.example.timer.ViewModel.EditViewModel

class EditActivity : AppCompatActivity() {
    private var mViewModel: EditViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Settings.installSettings(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        mViewModel = ViewModelProvider(this).get(EditViewModel::class.java);
        val editName = findViewById<View>(R.id.name_edit_text) as EditText
        editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                mViewModel!!.setName(s.toString())
            }
        })
        val editDuration = findViewById<View>(R.id.duration_edit_text) as EditText
        editDuration.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                mViewModel!!.setDuration(s.toString())
            }
        })
        val recyclerView = findViewById<View>(R.id.timers_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TimerAdapter(
            mViewModel!!.getTimers()!!,
            mViewModel!!.name!!,
            mViewModel!!.getDuration()
        )
        recyclerView.adapter = adapter
        val btn = findViewById<View>(R.id.add_timer_button) as Button
        btn.setOnClickListener {
            mViewModel!!.AddTimer(editName.text.toString(), editDuration.text.toString())
            adapter.notifyDataSetChanged()
        }
    }
}