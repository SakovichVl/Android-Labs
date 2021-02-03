package com.example.timer.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.RecyclerView.SequenceAdapter
import com.example.timer.ViewModel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private var mViewModel: HomeViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val name = findViewById<EditText>(R.id.name_sequence_edit_text)
        val color = findViewById<EditText>(R.id.color_sequence_edit_text)
        val recyclerView = findViewById<RecyclerView>(R.id.sequence_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SequenceAdapter(mViewModel!!.getSequences())
        recyclerView.adapter = adapter
        val btn = findViewById<View>(R.id.add_sequence_button) as Button
        btn.setOnClickListener {
            mViewModel!!.addSequence(name.text.toString(), color.text.toString())
            adapter.notifyDataSetChanged()
        }
    }
}