package com.example.lab2

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.*
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.Preference.OnPreferenceClickListener
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatDelegate
import com.example.lab2.App.Companion.instance
import com.example.lab2.DB.AppDatabase
import java.util.*

class Settings : PreferenceActivity() {
    private var preferences: SharedPreferences? = null
    private var indexOfFontScale = 0
    private var indexOfLanguage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val configuration = Configuration()
        initializing(configuration)
        baseContext.resources.updateConfiguration(configuration, null)
        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
        super.onCreate(savedInstanceState)
    }

    private fun initializing(configuration: Configuration) {
        if (preferences!!.getBoolean("theme", true)) {
            setTheme(R.style.Theme_AppCompat)
        }
        configuration.locale = langInit()
        configuration.fontScale = fontInit()
    }

    private fun langInit(): Locale {
        val value = preferences!!.getString("test_lang", "ru")
        val locale = Locale(value)
        Locale.setDefault(locale)
        indexOfLanguage = Arrays.asList(*resources.getStringArray(R.array.language_alias)).indexOf(value)
        return locale
    }

    private fun fontInit(): Float {
        val font = preferences!!.getString("font_size", "1.0")
        indexOfFontScale = Arrays.asList(*resources.getStringArray(R.array.text_scale_alias)).indexOf(font)
        return font!!.toFloat()
    }

    class SettingsFragment : PreferenceFragment() {
        private var db: AppDatabase? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            db = instance!!.db
            addPreferencesFromResource(R.xml.preference)
            val theme = findPreference("theme")
            theme.onPreferenceChangeListener = OnPreferenceChangeListener { preference: Preference?, newValue: Any ->
                if (newValue as Boolean) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                activity.recreate()
                true
            }
            val font = findPreference("font_size") as ListPreference
            font.onPreferenceChangeListener = OnPreferenceChangeListener { preference: Preference?, newValue: Any ->
                val configuration = resources.configuration
                val metrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(metrics)
                metrics.scaledDensity = newValue.toString().toFloat() * metrics.density
                activity.baseContext.resources.updateConfiguration(configuration, metrics)
                activity.recreate()
                true
            }
            font.setValueIndex((activity as Settings).indexOfFontScale)
            val language = findPreference("test_lang") as ListPreference
            language.onPreferenceChangeListener = OnPreferenceChangeListener { preference: Preference?, newValue: Any ->
                val locale = Locale(newValue.toString())
                Locale.setDefault(locale)
                val configuration = Configuration()
                configuration.locale = locale
                activity.resources.updateConfiguration(configuration, null)
                activity.recreate()
                true
            }
            language.setValueIndex((activity as Settings).indexOfLanguage)
            findPreference("DeleteAll").onPreferenceClickListener = OnPreferenceClickListener { `val`: Preference? ->
                db!!.tDAO()!!.DeleteTimers()
                activity.setResult(RESULT_OK, Intent())
                activity.finish()
                true
            }
        }
    }
}