package com.kaus.wordsearch.features

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kaus.wordsearch.R
import com.kaus.wordsearch.utilities.preferences.DEFAULT_LANGUAGE
import com.kaus.wordsearch.utilities.preferences.Prefs
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setLocale()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    @Suppress("DEPRECATION")
    private fun setLocale() {
        val resources: Resources = resources
        val dm: DisplayMetrics = resources.displayMetrics
        val config: Configuration = resources.configuration
        config.setLocale(Locale(Prefs.getString(DEFAULT_LANGUAGE, "en")))
        resources.updateConfiguration(config, dm)
    }
}