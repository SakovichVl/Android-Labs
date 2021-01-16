package com.example.converter.Actifity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.converter.Enum.CurrencyEnum
import com.example.converter.Enum.DistanceEnum
import com.example.converter.Enum.WeightEnum
import com.example.converter.R
import com.example.converter.ViewModel.ConverterViewModel

class ConverterActivity : AppCompatActivity() {
    lateinit var inputS: Spinner
    lateinit var outputS: Spinner
    var converterViewModel: ConverterViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)
        converterViewModel = ViewModelProviders.of(this).get(ConverterViewModel::class.java)
        inputS = findViewById(R.id.inputS)
        outputS = findViewById(R.id.outputS)
        val intent = intent
        converterViewModel!!.setEnumName(intent.getStringExtra("value"))
        when (intent.getStringExtra("value")) {
            "d" -> {
                inputS.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_item, DistanceEnum.values()))
                outputS.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_item, DistanceEnum.values()))
            }
            "w" -> {
                inputS.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_item, WeightEnum.values()))
                outputS.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_item, WeightEnum.values()))
            }
            "c" -> {
                inputS.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_item, CurrencyEnum.values()))
                outputS.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_item, CurrencyEnum.values()))
            }
        }
    }
}