package com.example.lab2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2.DB.AppDatabase
import com.example.lab2.DB.TimerModel

class TimerAdapter internal constructor(context: Context?, private val layout: Int, private val timerModelList: MutableList<TimerModel>, private val db: AppDatabase) : RecyclerView.Adapter<TimerAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(layout, parent, false)
        context = view.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timerModel = timerModelList[position]
        holder.nameView.text = timerModel.Name
        holder.idView.text = Integer.toString(timerModel.Id)
        holder.layout.setBackgroundColor(timerModel.ColorDif)
        holder.layout.setOnClickListener { v: View? ->
            val intent = Intent(context, WorkoutActivity::class.java)
            intent.putExtra("timerId", timerModel.Id)
            context!!.startActivity(intent)
        }
        holder.removeButton.setOnClickListener { i: View? -> startTimer(timerModel, position) }
        holder.editButton.setOnClickListener { i: View? -> editTimer(timerModel) }
    }

    private fun startTimer(timerModel: TimerModel, position: Int) {
        db.tDAO()!!.delete(timerModelList[position])
        timerModelList.remove(timerModel)
        notifyDataSetChanged()
    }

    private fun editTimer(timerModel: TimerModel) {
        val intent = Intent(context, CreateActivity::class.java)
        intent.putExtra("timerId", intArrayOf(timerModel.Id, 1)).flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context!!.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return timerModelList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val removeButton: ImageButton
        val editButton: ImageButton
        val nameView: TextView
        val idView: TextView
        val layout: LinearLayout

        init {
            removeButton = view.findViewById(R.id.itemDeleteButton)
            editButton = view.findViewById(R.id.itemEditButton)
            nameView = view.findViewById(R.id.nameOfItem)
            idView = view.findViewById(R.id.itemIdView)
            layout = view.findViewById(R.id.timerListItem)
        }
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}