package com.example.lab2.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TimerModel {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var Id = 0
    @JvmField
    var Name: String? = null
    @JvmField
    var PreparingTime = 0
    @JvmField
    var WorkingTime = 0
    @JvmField
    var RestingTime = 0
    @JvmField
    var NumOfCycles = 0
    @JvmField
    var NumOfSets = 0
    @JvmField
    var NumOfRestSets = 0
    @JvmField
    var ColorDif = 0
}