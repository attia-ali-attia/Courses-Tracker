package com.courses.tracker.data.local

import android.util.Log
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListOfIntegers(listOfDays: List<Int>): String {
        val stringBuilder = StringBuilder()
        for (day in listOfDays) {
            stringBuilder.append("$day,")
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndex)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun stringToListOfIntegers(days: String): List<Int> {
        return days.split(",").map { it.toInt() }
    }
}