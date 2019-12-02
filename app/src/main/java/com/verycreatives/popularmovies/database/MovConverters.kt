package com.verycreatives.popularmovies.database

import androidx.room.TypeConverter
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Collections.emptyList




class MovConverters {
    @TypeConverter
    fun fromTimestamp(mills: Long?): Date? {
        return if (mills == null) null else Date(mills)
    }

    @TypeConverter
    fun fromDate(date: Date?) : Long?  {
        return date?.time
    }

    /*@TypeConverter
    fun fromIntegersToStr(ints: ArrayList<Int>): String {
        var value = ""

        for (i in ints)
            value += "$i,"

        return value
    }

    @TypeConverter
    fun fromStringToInt(str: String) : ArrayList<Int>  {
        val list = listOf(str.split("\\s*,\\s*"))
        return list
    }*/

    @TypeConverter
    fun storedStringToIntArray(data: String?): ArrayList<Int> {
        val gson = Gson()
        if (data == null) {
            return ArrayList()
        }
        val listType = object : TypeToken<ArrayList<Int>>() { }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun intArrayToStoredString(ints: ArrayList<Int>): String {
        val gson = Gson()
        return gson.toJson(ints)
    }

}