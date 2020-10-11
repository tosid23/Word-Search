package com.kaus.wordsearch.utilities.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kaus.wordsearch.App
import com.kaus.wordsearch.model.PuzzleData
import com.kaus.wordsearch.utilities.DATABASE_NAME
import com.kaus.wordsearch.utilities.room.dao.PuzzleDataDao

@Database(
    entities = [PuzzleData::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomDb : RoomDatabase() {

    companion object {

        // For Singleton instantiation
        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getInstance(): RoomDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(App.instance).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): RoomDb {
            return Room.databaseBuilder(context, RoomDb::class.java, DATABASE_NAME)
                .build()
        }
    }

    abstract fun puzzleDataDao(): PuzzleDataDao


}