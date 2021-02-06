package com.example.timer.RecyclerView

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.Activity.SequencePage
import com.example.timer.Activity.TimersActivity
import com.example.timer.Database.Database.deleteSequence
import com.example.timer.Model.Sequence
import com.example.timer.R
import java.util.*

class SequenceAdapter(private val sequences: ArrayList<Sequence>) :
    RecyclerView.Adapter<SequenceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sequence_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sequence = sequences[position]
        holder.name.text = sequence.name
        holder.color = sequence.color
    }

    override fun getItemCount(): Int {
        return sequences.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var color = 0

        init {
            name = itemView.findViewById(R.id.name_sequence)
            var btn = itemView.findViewById<Button>(R.id.del_sequence_button)
            btn.setOnClickListener { v ->
                deleteSequence(v.context, sequences[adapterPosition].id)
                sequences.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
            btn = itemView.findViewById(R.id.change_sequence_button)
            btn.setOnClickListener { v ->
                val intent = Intent(v.context, SequencePage::class.java)
                intent.putExtra("sequence_id", sequences[adapterPosition].id)
                v.context.startActivity(intent)
            }
            btn = itemView.findViewById(R.id.timers_page_button)
            btn.setOnClickListener { v ->
                val intent = Intent(v.context, TimersActivity::class.java)
                intent.putExtra("sequence_id", sequences[adapterPosition].id)
                v.context.startActivity(intent)
            }
        }
    }
}