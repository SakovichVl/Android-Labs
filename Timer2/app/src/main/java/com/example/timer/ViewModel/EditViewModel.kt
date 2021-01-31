package com.example.timer.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timer.Database.Database
import com.example.timer.Model.Timer
import java.util.*

class EditViewModel(application: Application) : AndroidViewModel(application) {
    private var timers: ArrayList<Timer>? = null
    var name: MutableLiveData<String>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }
        private set
    private var duration: MutableLiveData<String>? = null
    fun AddTimer(name: String?, duration: String) {
        val time = duration.toInt()
        if (time != 0) {
            val timer = Timer(name!!, time)
            Database.InsertTimer(timer, getApplication<Application>().baseContext)
            timers!!.add(timer)
        }
    }

    fun getTimers(): ArrayList<Timer>? {
        if (timers == null) {
            timers = Database.getTimers(getApplication<Application>().baseContext)
        }
        return timers
    }

    fun setName(name: String) {
        this.name!!.value = name
    }

    fun getDuration(): MutableLiveData<String> {
        if (duration == null) {
            duration = MutableLiveData()
        }
        return duration!!
    }

    fun setDuration(duration: String) {
        this.duration!!.value = duration
    }
}