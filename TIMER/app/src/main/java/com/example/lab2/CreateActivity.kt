package com.example.lab2

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar
import codes.side.andcolorpicker.model.IntegerHSLColor
import com.example.lab2.App.Companion.instance
import com.example.lab2.DB.AppDatabase
import com.example.lab2.DB.TimerModel

class CreateActivity : AppCompatActivity() {
    lateinit var nameInput: EditText
    lateinit var preparationTimeInput: EditText
    lateinit var workingTimeInput: EditText
    lateinit var restingTimeInput: EditText
    lateinit var numOfCyclesInput: EditText
    lateinit var numOfSetsInput: EditText
    lateinit var calmTimeInput: EditText
    lateinit var colorPicker: HSLColorPickerSeekBar
    lateinit var db: AppDatabase
    lateinit var vm: CreateViewModel
    lateinit var timerModel: TimerModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        db = instance?.db!!
        vm = ViewModelProvider(this).get(CreateViewModel::class.java)
        findViewsInputs()
        findViewsButtons()
        setObservers()
        initInputs(ids)
    }

    private fun findViewsInputs() {
        nameInput = findViewById(R.id.nameTextView)
        preparationTimeInput = findViewById(R.id.preparationInput)
        workingTimeInput = findViewById(R.id.workInput)
        restingTimeInput = findViewById(R.id.restInput)
        numOfCyclesInput = findViewById(R.id.cycleInput)
        numOfSetsInput = findViewById(R.id.setsInput)
        calmTimeInput = findViewById(R.id.calmInput)
        colorPicker = findViewById(R.id.colorPicker)
        nameInput.setOnKeyListener(View.OnKeyListener { v: View?, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                vm!!.setName(nameInput.getText().toString())
                if (keyCode == 4) {
                    val backIntent = Intent(applicationContext, MainActivity::class.java)
                    backIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(backIntent)
                    finish()
                    return@OnKeyListener true
                }
                return@OnKeyListener true
            }
            false
        })
    }

    private fun findViewsButtons() {
        findViewById<View>(R.id.prepTimeIncrease).setOnClickListener { i: View? -> vm!!.preparationIncrement() }
        findViewById<View>(R.id.prepTimeDecrease).setOnClickListener { i: View? -> vm!!.preparationDecrement() }
        findViewById<View>(R.id.workTimeIncrease).setOnClickListener { i: View? -> vm!!.workTimeIncrement() }
        findViewById<View>(R.id.workTimeDecrease).setOnClickListener { i: View? -> vm!!.workTimeDecrement() }
        findViewById<View>(R.id.restTimeIncrease).setOnClickListener { i: View? -> vm!!.restTimeIncrement() }
        findViewById<View>(R.id.restTimeDecrease).setOnClickListener { i: View? -> vm!!.restTimeDecrement() }
        findViewById<View>(R.id.numOfCyclesIncrease).setOnClickListener { i: View? -> vm!!.cycleIncrement() }
        findViewById<View>(R.id.numOfCyclesDecrease).setOnClickListener { i: View? -> vm!!.cycleDecrement() }
        findViewById<View>(R.id.numOfSetsIncrease).setOnClickListener { i: View? -> vm!!.setsIncrement() }
        findViewById<View>(R.id.numOfSetsDecrease).setOnClickListener { i: View? -> vm!!.setsDecrement() }
        findViewById<View>(R.id.calmTimeIncrease).setOnClickListener { i: View? -> vm!!.restSetsIncrement() }
        findViewById<View>(R.id.calmTimeDecrease).setOnClickListener { i: View? -> vm!!.restSetsDecrement() }
        findViewById<View>(R.id.cancelButton).setOnClickListener { i: View? -> quit() }
        findViewById<View>(R.id.saveButton).setOnClickListener { i: View? ->
            val ids = ids
            if (ids!![1] != 1) {
                val model = TimerModel()
                modelInit(model)
                db!!.tDAO()!!.insert(model)
            } else {
                modelInit(timerModel)
                db!!.tDAO()!!.update(timerModel)
            }
            val backIntent = Intent(applicationContext, MainActivity::class.java)
            backIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(backIntent)
            finish()
            quit()
        }
    }

    private val ids: IntArray?
        private get() {
            val bundle = intent.extras
            return bundle!!["timerId"] as IntArray?
        }

    private fun initInputs(ids: IntArray?) {
        if (ids!![1] == 1) {
            timerModel = db!!.tDAO()!!.getTimerById(ids[0].toLong())!!
            timerModel!!.Name?.let { vm!!.setName(it) }
            vm!!.setPreparingTime(timerModel!!.PreparingTime)
            vm!!.setWorkingTime(timerModel!!.WorkingTime)
            vm!!.setRestingTime(timerModel!!.RestingTime)
            vm!!.setNumOfCycles(timerModel!!.NumOfCycles)
            vm!!.setNumOfSets(timerModel!!.NumOfSets)
            vm!!.setNumOfRestSets(timerModel!!.NumOfRestSets)
            vm!!.setColorDif(timerModel!!.ColorDif)
            colorPicker!!.pickedColor = convertToIntegerHSLColor(timerModel!!.ColorDif)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObservers() {
        vm!!.name.observe(this, { `val`: String? -> nameInput!!.setText(`val`) })
        vm!!.preparationTime.observe(this, { `val`: Int -> preparationTimeInput!!.setText(`val`.toString()) })
        vm!!.workingTime.observe(this, { `val`: Int -> workingTimeInput!!.setText(`val`.toString()) })
        vm!!.restingTime.observe(this, { `val`: Int -> restingTimeInput!!.setText(`val`.toString()) })
        vm!!.numOfCycles.observe(this, { `val`: Int -> numOfCyclesInput!!.setText(`val`.toString()) })
        vm!!.numOfSets.observe(this, { `val`: Int -> numOfSetsInput!!.setText(`val`.toString()) })
        vm!!.numOfRestSets.observe(this, { `val`: Int -> calmTimeInput!!.setText(`val`.toString()) })
    }

    private fun quit() {
        val backIntent = Intent(applicationContext, MainActivity::class.java)
        backIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(backIntent)
        finish()
    }

    private fun modelInit(timerModel: TimerModel?) {
        timerModel!!.Name = nameInput!!.text.toString()
        timerModel.PreparingTime = preparationTimeInput!!.text.toString().toInt()
        timerModel.WorkingTime = workingTimeInput!!.text.toString().toInt()
        timerModel.RestingTime = restingTimeInput!!.text.toString().toInt()
        timerModel.NumOfCycles = numOfCyclesInput!!.text.toString().toInt()
        timerModel.NumOfSets = numOfSetsInput!!.text.toString().toInt()
        timerModel.NumOfRestSets = calmTimeInput!!.text.toString().toInt()
        val color = colorPicker!!.pickedColor
        timerModel.ColorDif = Color.HSVToColor(floatArrayOf(color.floatH, color.floatL, color.floatS))
    }

    private fun convertToIntegerHSLColor(color: Int): IntegerHSLColor {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        val integerHSLColor = IntegerHSLColor()
        integerHSLColor.floatH = hsv[0]
        integerHSLColor.floatL = hsv[1]
        integerHSLColor.floatS = hsv[2]
        return integerHSLColor
    }
}