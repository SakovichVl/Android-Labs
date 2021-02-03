package com.example.timer.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.timer.Database.Database.InsertSequence
import com.example.timer.Database.Database.getSequences
import com.example.timer.Model.Sequence
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var sequences: ArrayList<Sequence>? = null
    fun getSequences(): ArrayList<Sequence> {
        if (sequences == null) {
            sequences = getSequences(getApplication<Application>().baseContext)
        }
        return sequences as ArrayList<Sequence>
    }

    fun addSequence(name: String?, color: String?) {
        val sequence = Sequence(name, color)
        InsertSequence(getApplication<Application>().baseContext, sequence)
        sequences!!.add(sequence)
    }
}