package com.example.timer.Model

import android.os.Parcel
import android.os.Parcelable

class Timer : Parcelable {
    var id: Long = 0
    var name: String? = null
    var duration = 0

    constructor(parcel: Parcel){
        id = parcel.readLong()
        name = parcel.readString()
        duration = parcel.readInt()
    }

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

    companion object CREATOR : Parcelable.Creator<Timer> {
        override fun createFromParcel(parcel: Parcel): Timer {
            return Timer(parcel)
        }

        override fun newArray(size: Int): Array<Timer?> {
            return arrayOfNulls(size)
        }
    }
}