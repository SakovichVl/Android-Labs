package com.example.timer.Service

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.os.CountDownTimer
import android.os.IBinder
import com.example.timer.Model.Sequence
import com.example.timer.R

class TimeService : Service() {
    private var sequence: Sequence? = null
    private var position = 0
    var countDownTimer: CountDownTimer? = null
    private val soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
    private var soundId = 0
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        soundId = soundPool.load(this, R.raw.sound, 1)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        sequence = intent.getParcelableExtra("Sequence")
        val type = intent.getStringExtra("Type")
        when (type) {
            "Start" -> startTimer()
            "Stop" -> stopTimer()
            "Next" -> nextTimer()
            "Prev" -> prevTimer()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startTimer() {
        if (sequence!!.timers != null && sequence!!.timers!!.size > 0) {
            countDownTimer = object :
                CountDownTimer((sequence!!.timers!![position].duration * 1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    updateTime(millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    position++
                    if (position < sequence!!.timers!!.size) {
                        countDownTimer!!.cancel()
                        startTimer()
                    } else {
                        position = 0
                        updateTime(0)
                        stopSelf()
                    }
                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                }
            }
        }
        countDownTimer!!.start()
    }

    private fun updateTime(time: Long) {
        val intent = Intent("Broadcast")
        intent.putExtra("Time", time)
        intent.putExtra("Name", sequence!!.timers!![position].name)
        sendBroadcast(intent)
    }

    private fun stopTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    private fun nextTimer() {
        if (position < sequence!!.timers!!.size - 1) {
            position++
            countDownTimer!!.cancel()
            startTimer()
        }
    }

    private fun prevTimer() {
        if (position > 0) {
            position--
            countDownTimer!!.cancel()
            startTimer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }
}