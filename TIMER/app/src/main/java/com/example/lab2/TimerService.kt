package com.example.lab2

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.media.SoundPool
import android.os.IBinder
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class TimerService : Service() {
    private var scheduledFuture: ScheduledFuture<*>? = null
    private var service: ScheduledExecutorService? = null
    private var soundPool: SoundPool? = null
    private var name: String? = null
    lateinit var preparationSound  : MediaPlayer
    lateinit var finishSound :MediaPlayer
    private var currentTime = 0
    override fun onCreate() {
        super.onCreate()
        soundPool = SoundPool.Builder().setMaxStreams(5).build()
        preparationSound = create(applicationContext,R.raw.preparing)
        finishSound = create(applicationContext,R.raw.finishing)
        service = Executors.newScheduledThreadPool(1)
    }

    override fun onDestroy() {
        service!!.shutdownNow()
        scheduledFuture!!.cancel(true)
        val intent = Intent(WorkoutActivity.BROADCAST_ACTION)
        intent.putExtra(WorkoutActivity.CURRENT_ACTION, "pause")
                .putExtra(WorkoutActivity.NAME_ACTION, name)
                .putExtra(WorkoutActivity.TIME_ACTION, Integer.toString(currentTime))
        sendBroadcast(intent)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startsId: Int): Int {
        name = intent.getStringExtra(WorkoutActivity.NAME_ACTION)
        val serviceTimerTask = ServiceTimerTask(intent.getStringExtra(WorkoutActivity.PARAMS_START_TIME)!!.toInt(), name)
        if (scheduledFuture != null) {
            service!!.schedule({
                scheduledFuture!!.cancel(true)
                scheduledFuture = service!!.scheduleAtFixedRate(serviceTimerTask, 0, (intent.getStringExtra(WorkoutActivity.PARAMS_START_TIME)!!.toInt() + 1).toLong(), TimeUnit.SECONDS)
            }, 1, TimeUnit.SECONDS)
        } else {
            scheduledFuture = service!!.scheduleAtFixedRate(serviceTimerTask, 0, (intent.getStringExtra(WorkoutActivity.PARAMS_START_TIME)!!.toInt() + 1).toLong(), TimeUnit.SECONDS)
        }
        return super.onStartCommand(intent, flags, startsId)
    }

    internal inner class ServiceTimerTask(private val time: Int, private val name: String?) : TimerTask() {
        override fun run() {
            var intent = Intent(WorkoutActivity.BROADCAST_ACTION)
            if (name == resources.getString(R.string.finish)) {
                intent.putExtra(WorkoutActivity.CURRENT_ACTION, "work")
                        .putExtra(WorkoutActivity.NAME_ACTION, name)
                        .putExtra(WorkoutActivity.TIME_ACTION, "")
                sendBroadcast(intent)
            }
            try {
                currentTime = time
                while (currentTime > 0) {
                    intent.putExtra(WorkoutActivity.CURRENT_ACTION, "work")
                            .putExtra(WorkoutActivity.NAME_ACTION, name)
                            .putExtra(WorkoutActivity.TIME_ACTION, Integer.toString(currentTime))
                    sendBroadcast(intent)
                    TimeUnit.SECONDS.sleep(1)
                    signalSound(currentTime)
                    currentTime--
                }
                intent = Intent(WorkoutActivity.BROADCAST_ACTION)
                intent.putExtra(WorkoutActivity.CURRENT_ACTION, "clear")
                sendBroadcast(intent)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        private fun signalSound(time: Int) {
            if (time <= 4) {
                when (time) {
                    1 -> finishSound.start()
                    else -> preparationSound.start()
                }
            }
        }
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }
}