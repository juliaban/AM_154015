package com.example.sladamiprzygod

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.sladamiprzygod.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val preferences: SharedPreferences by lazy {
        getSharedPreferences("themePrefs", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setThemeFromPreferences()

        Thread.sleep(3000)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonRozpocznij.setOnClickListener{
            val nowaAktywnosc = Intent(applicationContext, NextActivity::class.java)
            startActivity(nowaAktywnosc)
        }

        binding.themeSwitch!!.isChecked = isDarkTheme()
        binding.themeSwitch!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            saveThemePreference(isChecked)
        }
    }

    private fun setThemeFromPreferences() {
        if (isDarkTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun isDarkTheme(): Boolean {
        return preferences.getBoolean("dark_theme", false)
    }

    private fun saveThemePreference(isDarkTheme: Boolean) {
        with(preferences.edit()) {
            putBoolean("dark_theme", isDarkTheme)
            apply()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("dark_theme", isDarkTheme())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val isDarkTheme = savedInstanceState.getBoolean("dark_theme", false)
        binding.themeSwitch!!.isChecked = isDarkTheme
    }
}
