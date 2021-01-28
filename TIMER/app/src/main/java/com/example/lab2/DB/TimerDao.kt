package com.example.lab2.DB

import androidx.room.*

@Dao
interface TimerDao {
    @get:Query("SELECT * FROM timerModel")
    val allTimers: List<TimerModel?>?

    @Query("SELECT * FROM timerModel WHERE id = :id")
    fun getTimerById(id: Long): TimerModel?

    @Insert
    fun insert(timerModel: TimerModel?)

    @Update
    fun update(timerModel: TimerModel?)

    @Delete
    fun delete(timerModel: TimerModel?)

    @Query("DELETE FROM timerModel")
    fun DeleteTimers()
}