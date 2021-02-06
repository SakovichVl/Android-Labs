package com.example.timer.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.Model.Timer
import com.example.timer.R
import java.util.*

class AllTimersAdapter(
    private val timers: ArrayList<Timer>,
    private val timer: MutableLiveData<Timer>
) : RecyclerView.Adapter<AllTimersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.timer_sequence_item, parent, false)
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
        var btn: Button

        init {
            name = itemView.findViewById<View>(R.id.name_timer_sequence_text_view) as TextView
            duration =
                itemView.findViewById<View>(R.id.duration_timer_sequence_text_view) as TextView
            btn = itemView.findViewById(R.id.add_timer_sequence_button)
            btn.text = "+"
            btn.setOnClickListener { timer.setValue(timers[adapterPosition]) }
        }
    }
}