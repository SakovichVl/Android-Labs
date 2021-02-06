package com.example.timer.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timer.Database.Database.getSequence
import com.example.timer.Model.Sequence
import com.example.timer.Model.Timer
import java.util.*

class TimersPageViewModel(application: Application) : AndroidViewModel(application) {
    var sequence: Sequence? = null
        private set
    private var time: MutableLiveData<String>? = null
    private var name: MutableLiveData<String>? = null
    fun setSequence(id: Long) {
        sequence = getSequence(getApplication<Application>().baseContext, id)
    }

    val nameSequence: String?
        get() = sequence!!.name
    val colorSequence: Int
        get() = sequence!!.color
    val timers: ArrayList<Timer>?
        get() = sequence!!.timers
    val timer: MutableLiveData<String>
        get() {
            if (time == null) {
                time = MutableLiveData()
            }
            return time!!
        }

    fun getName(): MutableLiveData<String> {
        if (name == null) {
            name = MutableLiveData()
        }
        return name!!
    }

    fun setTime(time: String) {
        this.time!!.value = time
    }

    fun setName(name: String) {
        this.name!!.value = name
    }
}