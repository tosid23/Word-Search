package com.kaus.wordsearch

import android.app.Application
import android.content.ContextWrapper
import androidx.room.Room
import com.kaus.wordsearch.utilities.DATABASE_NAME
import com.kaus.wordsearch.utilities.preferences.Prefs
import com.kaus.wordsearch.utilities.room.RoomDb

class App : Application() {

    companion object {
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize helper classes & libraries
        instance = this
        initializePrefs()
        initializeDB()
    }

    private fun initializePrefs() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

    private fun initializeDB() {
        Room.databaseBuilder(applicationContext, RoomDb::class.java, DATABASE_NAME).build()
    }

}