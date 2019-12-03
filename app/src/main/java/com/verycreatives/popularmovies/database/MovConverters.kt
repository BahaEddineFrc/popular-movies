package com.verycreatives.popularmovies.database

import androidx.room.TypeConverter
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken




class MovConverters {
    @TypeConverter
    fun fromTimestamp(mills: Long?): Date? {
        return if (mills == null) null else Date(mills)
    }

    @TypeConverter
    fun fromDate(date: Date?) : Long?  {
        return date?.time
    }

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
    fun intArrayToStoredString(ints: ArrayList<Int>?): String {
        val gson = Gson()
        if (ints == null) {
            return ""
        }
        return gson.toJson(ints)
    }

}