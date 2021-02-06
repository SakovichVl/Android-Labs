package com.example.timer.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.Model.Timer
import com.example.timer.R
import java.util.*

class TimerInTimerPageAdapter(timers: ArrayList<Timer>?) :
    RecyclerView.Adapter<TimerInTimerPageAdapter.ViewHolder>() {
    private val timers: ArrayList<Timer>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.timer_item_in_timer_page, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timer = timers[position]
        holder.name.text = timer.name
        holder.duration.text = timer.duration.toString()
    }

    override fun getItemCount(): Int {
        return timers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var duration: TextView

        init {
            name = itemView.findViewById<View>(R.id.name_timer_text_view) as TextView
            duration = itemView.findViewById<View>(R.id.duration_timer_text_view) as TextView
        }
    }

    init {
        var timers = timers
        if (timers == null) {
            timers = ArrayList()
        }
        this.timers = timers
    }
}