package com.example.timer.Activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.example.timer.R
import com.example.timer.Settings.Settings.installSettings
import com.example.timer.ViewModel.SettingViewModel

class SettingActivity : AppCompatActivity() {
    private var mViewModel: SettingViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        installSettings(this)
        val config = Configuration(resources.configuration)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        mViewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        val idTheme = sharedPreferences.getInt("Theme", R.style.AppTheme)
        mViewModel!!.theme = idTheme == R.style.DarkTheme
        var switchCompat = findViewById<SwitchCompat>(R.id.theme_switch)
        switchCompat.isChecked = mViewModel!!.theme
        switchCompat.setOnCheckedChangeListener { buttonView, isChecked ->
            mViewModel!!.theme = isChecked
        }
        switchCompat = findViewById(R.id.language_switch)
        mViewModel!!.isLanguage = sharedPreferences.getBoolean("Language", true)
        switchCompat.isChecked = mViewModel!!.isLanguage
        switchCompat.setOnCheckedChangeListener { buttonView, isChecked ->
            mViewModel!!.isLanguage = isChecked
        }
        var btn = findViewById<Button>(R.id.delete_all_data_button)
        btn.setOnClickListener { mViewModel!!.delete() }
        val editText = findViewById<EditText>(R.id.font_size_edit_text)
        btn = findViewById(R.id.save_settings_button)
        btn.setOnClickListener {
            editor.putInt("Theme", if (mViewModel!!.theme) R.style.DarkTheme else R.style.AppTheme)
            editor.putFloat("Size_Font", mViewModel!!.checkSize(editText.text.toString()))
            editor.apply()
            mViewModel!!.language(config)
            val intent = Intent(applicationContext, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}