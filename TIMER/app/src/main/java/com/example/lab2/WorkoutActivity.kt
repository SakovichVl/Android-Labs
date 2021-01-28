package com.example.lab2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lab2.App.Companion.instance
import com.example.lab2.DB.TimerModel
import java.util.*

class WorkoutActivity : AppCompatActivity() {
    lateinit var timerModel: TimerModel
    lateinit var receiver: BroadcastReceiver
    lateinit var adapter: ArrayAdapter<String>
    lateinit var listTraining: ListView
    lateinit var startBtn: ImageButton
    private val workList = ArrayList<String>()
    private var element = 0
    private var isLastSecond = false
    private var valueStatusPause: String? = ""
    private var valueTimePause: String? = ""
    private var valueElementPause = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        val intent = intent
        val bundle = intent.extras
        val itemId = bundle!!["timerId"] as Int
        timerModel = instance!!.db!!.tDAO()!!.getTimerById(itemId.toLong())!!
        setSupportActionBar(findViewById(R.id.mainToolbar))
        Objects.requireNonNull(supportActionBar)?.setTitle(timerModel!!.Name)
        findViewById<View>(R.id.mainToolbar).setBackgroundColor(timerModel!!.ColorDif)
        startBtn = findViewById(R.id.startButton)
        listTraining = findViewById(R.id.items)
        registerReceiver(createReceiver(), IntentFilter(BROADCAST_ACTION))
        fillAdapter(timerModel)
        startBtn.setOnClickListener(View.OnClickListener { view: View? -> onStartClick(view) })
        startBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.start))
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, workList)
        listTraining.setAdapter(adapter)
        listTraining.setOnItemClickListener(OnItemClickListener { parent: AdapterView<*>?, view: View, position: Int, id: Long -> ChangeFieldListView(view, position) })
        listTraining.setOnScrollListener(createOnScrollListener())
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, TimerService::class.java))
    }

    override fun onBackPressed() {
        unregisterReceiver(receiver)
        stopService(Intent(this, TimerService::class.java))
        finish()
    }

    fun onStartClick(view: View?) {
        if (valueStatusPause!!.isEmpty() || valueStatusPause == resources.getString(R.string.finish)) {
            element = 0
            isLastSecond = false
            stopService(Intent(this, TimerService::class.java))
            AddNewService(resources.getString(R.string.preparation), java.lang.String.valueOf(timerModel!!.PreparingTime))
            val childIndex = listTraining!!.lastVisiblePosition - listTraining!!.firstVisiblePosition
            listTraining!!.getChildAt(childIndex)
                    .setBackgroundColor(resources.getColor(R.color.colorPrimary, theme))
            if (element >= listTraining!!.firstVisiblePosition
                    && element <= listTraining!!.lastVisiblePosition) {
                listTraining!!.getChildAt(element)
                        .setBackgroundColor(resources.getColor(R.color.colorAccent, theme))
            }
        } else {
            element = valueElementPause
            AddNewService(valueStatusPause, valueTimePause)
        }
        startBtn!!.setOnClickListener { view: View? -> onResetClick(view) }
        startBtn!!.background = ContextCompat.getDrawable(this, R.drawable.stop)
    }

    private fun createOnScrollListener(): AbsListView.OnScrollListener {
        return object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}
            override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                                  visibleItemCount: Int, totalItemCount: Int) {
                for (i in 0 until visibleItemCount) {
                    listTraining!!.getChildAt(i).setBackgroundColor(resources.getColor(R.color.colorPrimary, theme))
                }
                if (element >= firstVisibleItem && element < firstVisibleItem + visibleItemCount && !isLastSecond) {
                    listTraining!!.getChildAt(element - firstVisibleItem)
                            .setBackgroundColor(resources.getColor(R.color.colorAccent, theme))
                }
                if (element - 1 >= firstVisibleItem && element - 1 < firstVisibleItem + visibleItemCount && isLastSecond) {
                    listTraining!!.getChildAt(element - 1 - firstVisibleItem)
                            .setBackgroundColor(resources.getColor(R.color.colorAccent, theme))
                }
            }
        }
    }

    private fun createReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.getStringExtra(CURRENT_ACTION) == "work") {
                    val task = intent.getStringExtra(NAME_ACTION)
                    val status = intent.getStringExtra(TIME_ACTION)
                    if (status == "1") {
                        lastSecondWork()
                    } else {
                        workInProgress(task)
                    }
                    (findViewById<View>(R.id.NamePart) as TextView).text = task
                    (findViewById<View>(R.id.timePart) as TextView).text = status
                } else if (intent.getStringExtra(CURRENT_ACTION) == "clear") {
                    clear()
                } else {
                    valueStatusPause = intent.getStringExtra(NAME_ACTION)
                    valueTimePause = intent.getStringExtra(TIME_ACTION)
                    startPause(valueTimePause)
                }
            }
        }
    }

    fun workInProgress(task: String?) {
        if (isLastSecond) {
            isLastSecond = false
        }
        valueStatusPause = ""
        if (task == resources.getString(R.string.finish)) {
            startBtn!!.setOnClickListener { view: View? -> onStartClick(view) }
            startBtn!!.background = ContextCompat.getDrawable(this, R.drawable.start)
        }
    }

    fun lastSecondWork() {
        element++
        isLastSecond = true
        if (element < adapter!!.count) {
            val words = Objects.requireNonNull(adapter!!.getItem(element))?.split(" : ")?.toTypedArray()
            val time = if (words?.size == 2) "0" else words?.get(2)
            AddNewService(words?.get(1), time)
        }
    }

    fun clear() {
        val index = element - listTraining!!.firstVisiblePosition
        if (element != 0 && index - 1 < 14 && index - 1 >= 0) {
            listTraining!!.getChildAt(index - 1)
                    .setBackgroundColor(resources.getColor(R.color.colorPrimary, theme))
        }
        if (index < 14 && index - 1 >= 0) {
            listTraining!!.getChildAt(index)
                    .setBackgroundColor(resources.getColor(R.color.colorAccent, theme))
        }
    }

    fun AddNewService(name: String?, time: String?) {
        startService(Intent(this, TimerService::class.java)
                .putExtra(PARAMS_START_TIME, time)
                .putExtra(NAME_ACTION, name))
    }

    fun startPause(status: String?) {
        if (status == "1") {
            if (!isLastSecond) {
                element--
            } else {
                isLastSecond = false
            }
            val words = Objects.requireNonNull(adapter!!.getItem(element))?.split(" : ")?.toTypedArray()
            valueStatusPause = words?.get(1)
        }
        valueElementPause = element
    }

    fun fillAdapter(workout: TimerModel?) {
        var number = 1
        var set = workout!!.NumOfSets
        if (workout.PreparingTime != 0) {
            workList.add(StringForTimer(number++, resources.getString(R.string.preparation), workout.PreparingTime))
        }
        while (set > 0) {
            for (cycle in workout.NumOfCycles downTo 1) {
                workList.add(StringForTimer(number++, resources.getString(R.string.work), workout.WorkingTime))
                workList.add(StringForTimer(number++, resources.getString(R.string.rest), workout.RestingTime))
            }
            set--
            if (set != 0 && workout.NumOfRestSets != 0) {
                workList.add(StringForTimer(number++, resources.getString(R.string.rest_between), workout.NumOfRestSets))
            }
        }
        workList.add(number.toString() + " : " + resources.getString(R.string.finish))
    }

    fun StringForTimer(number: Int, name: String, time: Int): String {
        return "$number : $name : $time"
    }

    fun ChangeFieldListView(view: View, position: Int) {
        for (i in 0 until listTraining!!.lastVisiblePosition - listTraining!!.firstVisiblePosition + 1) {
            listTraining!!.getChildAt(i).setBackgroundColor(resources.getColor(R.color.colorPrimary, theme))
        }
        element = position
        view.setBackgroundColor(resources.getColor(R.color.colorAccent, theme))
        stopService(Intent(this, TimerService::class.java))
        val words = Objects.requireNonNull(adapter!!.getItem(position))?.split(" : ")?.toTypedArray()
        if (words != null) {
            if (words.size == 2) {
                startBtn!!.background = ContextCompat.getDrawable(this, R.drawable.start)
                startBtn!!.setOnClickListener { view: View? -> onStartClick(view) }
                AddNewService(words?.get(1), "0")
            } else {
                startBtn!!.setOnClickListener { view: View? -> onResetClick(view) }
                startBtn!!.background = ContextCompat.getDrawable(this, R.drawable.stop)
                AddNewService(words?.get(1), words?.get(2))
            }
        }
    }

    fun onResetClick(view: View?) {
        startBtn!!.setOnClickListener { view: View? -> onStartClick(view) }
        startBtn!!.background = ContextCompat.getDrawable(this, R.drawable.start)
        stopService(Intent(this, TimerService::class.java))
    }

    companion object {
        const val PARAMS_START_TIME = "start_time"
        const val NAME_ACTION = "name"
        const val TIME_ACTION = "time"
        const val CURRENT_ACTION = "pause"
        const val BROADCAST_ACTION = "com.example.lab2"
    }
}