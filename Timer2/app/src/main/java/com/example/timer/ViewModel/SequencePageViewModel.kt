package com.example.timer.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timer.Database.Database.UpdateSequence
import com.example.timer.Database.Database.getSequence
import com.example.timer.Database.Database.getTimers
import com.example.timer.Model.Sequence
import com.example.timer.Model.Timer
import java.util.*

class SequencePageViewModel(application: Application) : AndroidViewModel(application) {
    var sequence: Sequence? = null
        private set
    var timer: MutableLiveData<Timer>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }
        private set
    val sequenceName: String?
        get() = sequence?.name
    val sequenceColor: Int
        get() = sequence!!.color

    fun setSequence(id: Long) {
        sequence = getSequence(getApplication<Application>().baseContext, id)
    }

    val timers: ArrayList<Timer>
        get() = getTimers(getApplication<Application>().baseContext)

    fun InsertTimerInSequence(timer: Timer) {
        timersInSequence!!.add(timer)
        UpdateSequence(getApplication<Application>().baseContext, sequence!!)
    }

    val timersInSequence: ArrayList<Timer>?
        get() {
            if (sequence!!.timers == null) {
                sequence!!.timers = ArrayList()
            }
            return sequence!!.timers
        }
}