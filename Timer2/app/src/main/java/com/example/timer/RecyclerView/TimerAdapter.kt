package com.example.timer.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.Database.Database
import com.example.timer.Model.Timer
import com.example.timer.R
import java.util.*

class TimerAdapter(
    private val timers: ArrayList<Timer>,
    val nameLiveData: MutableLiveData<String>,
    val durationLiveData: MutableLiveData<String>
) : RecyclerView.Adapter<TimerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_item, parent, false)
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
            name = itemView.findViewById<View>(R.id.name_timer_text_view) as TextView
            duration = itemView.findViewById<View>(R.id.duration_timer_text_view) as TextView
            btn = itemView.findViewById(R.id.del_timer_button)
            btn.setOnClickListener { v ->
                Database.deleteTimer(v.context, timers[adapterPosition].id)
                timers.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
            btn = itemView.findViewById(R.id.change_timer_button)
            btn.setOnClickListener { v ->
                val timer = timers[adapterPosition]
                if (nameLiveData.value != "") {
                    timer.name = nameLiveData.value!!
                }
                try {
                    timer.duration = durationLiveData.value!!.toInt()
                } catch (e: NumberFormatException) {
                    Toast.makeText(v.context, "You don't write number", Toast.LENGTH_LONG).show()
                }
                Database.UpdateTimer(v.context, timer)
                notifyDataSetChanged()
            }
        }
    }
}