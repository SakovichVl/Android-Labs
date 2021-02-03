package com.example.timer.Model

import android.os.Parcel
import android.os.Parcelable

class Timer : Parcelable {
    var id: Long = 0
    var name: String? = null
    var duration = 0

    constructor(name: String?, duration: Int) {
        this.name = name
        this.duration = duration
    }

    constructor(id: Long, name: String?, duration: Int) {
        this.id = id
        this.name = name
        this.duration = duration
    }

    constructor() {}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(name)
        dest.writeInt(duration)
    }

    companion object {
        val CREATOR: Parcelable.Creator<Timer?> = object : Parcelable.Creator<Timer?> {
            override fun createFromParcel(source: Parcel): Timer? {
                val id = source.readLong()
                val name = source.readString()
                val duration = source.readInt()
                return Timer(id, name, duration)
            }

            override fun newArray(size: Int): Array<Timer?> {
                return arrayOfNulls(size)
            }
        }
    }
}