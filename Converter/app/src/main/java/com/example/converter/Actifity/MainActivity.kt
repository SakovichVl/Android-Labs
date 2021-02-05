package com.example.converter.Actifity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.converter.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun mainBtnClick(view: View) {
        val intent = Intent(this@MainActivity, ConverterActivity::class.java)
        when (view.id) {
            R.id.btnDistance -> {
                intent.putExtra("value", "d")
                startActivity(intent)
            }
            R.id.btnWeight -> {
                intent.putExtra("value", "w")
                startActivity(intent)
            }
            R.id.btnCurrency -> {
                intent.putExtra("value", "c")
                startActivity(intent)
            }
        }
    }
}