package com.example.timer.Model

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Sequence : Parcelable {
    var id: Long = 0
    var name: String? = null
    var color = 0
    var timers: ArrayList<Timer>? = null

    constructor(name: String?, color: String?) {
        this.name = name
        try {
            this.color = Color.parseColor(color)
        } catch (ex: Exception) {
            this.color = Color.WHITE
        }
    }

    constructor(id: Long, name: String?, color: Int, timers: ArrayList<Timer>?) {
        this.id = id
        this.name = name
        this.color = color
        this.timers = timers
    }

    constructor() {}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(name)
        dest.writeInt(color)
        dest.writeTypedList(timers)
    }

    companion object {
        val CREATOR: Parcelable.Creator<Sequence?> = object : Parcelable.Creator<Sequence?> {
            override fun createFromParcel(source: Parcel): Sequence? {
                val id = source.readLong()
                val name = source.readString()
                val color = source.readInt()
                val timers = ArrayList<Timer>()
                source.readTypedList(timers as List<Timer?>, Timer.CREATOR)
                return Sequence(id, name, color, timers)
            }

            override fun newArray(size: Int): Array<Sequence?> {
                return arrayOfNulls(size)
            }
        }
    }
}