package com.kaus.wordsearch.utilities.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kaus.wordsearch.model.Word
import java.lang.reflect.Type

class Converters {

    /**
     * String to List and vise versa
     */
    @TypeConverter
    fun fromString(list: List<String>?): String? {
        if (list.isNullOrEmpty()) return null
        return Gson().toJson(list, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun toAString(string: String?): List<String>? {
        if (string == null) return null
        return Gson().fromJson<List<String>>(string, object : TypeToken<List<String>>() {}.type)
    }


    /**
     * Int to List and vise versa
     */
    @TypeConverter
    fun fromInt(list: List<Int>?): String? {
        if (list.isNullOrEmpty()) return null
        return Gson().toJson(list, object : TypeToken<List<Int>>() {}.type)
    }

    @TypeConverter
    fun toInt(string: String?): List<Int>? {
        if (string == null) return null
        return Gson().fromJson<List<Int>>(string, object : TypeToken<List<Int>>() {}.type)
    }

    /**
     * To & From ArrayList<Word>
     */
    @TypeConverter
    fun fromWordString(value: String?): List<Word?>? {
        val listType: Type = object : TypeToken<List<Word?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromWordArrayList(list: List<Word?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}