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

    constructor(parcel: Parcel){
        id = parcel.readLong()
        name = parcel.readString()
        color = parcel.readInt()
        timers = ArrayList<Timer>().apply{
            parcel.readTypedList(this, Timer.CREATOR)
        }
    }

    constructor(name: String?, color: String?){
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

    companion object CREATOR : Parcelable.Creator<Sequence> {
        override fun createFromParcel(parcel: Parcel): Sequence {
            return Sequence(parcel)
        }

        override fun newArray(size: Int): Array<Sequence?> {
            return arrayOfNulls(size)
        }
    }
}