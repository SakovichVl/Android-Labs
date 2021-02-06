package com.example.timer.ViewModel

import android.app.Application
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.example.timer.Database.Database
import java.util.*

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    var theme = false
    var isLanguage = false
    fun checkSize(number: String): Float {
        var result = 1f
        try {
            result = number.toFloat()
        } catch (ex: NumberFormatException) {
            Toast.makeText(
                getApplication<Application>().baseContext,
                "Font size error",
                Toast.LENGTH_LONG
            ).show()
        }
        if (result < 1 && result > 2) {
            Toast.makeText(
                getApplication<Application>().baseContext,
                "Font size [1..2]",
                Toast.LENGTH_LONG
            ).show()
            result = 1f
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun language(configuration: Configuration) {
        if (isLanguage) {
            configuration.setLocale(Locale("en"))
            Locale.setDefault(Locale("en"))
        } else {
            configuration.setLocale(Locale("ru"))
            Locale.setDefault(Locale("ru"))
        }
    }

    fun delete() {
        Database.deleteAll(getApplication<Application>().baseContext)
    }
}