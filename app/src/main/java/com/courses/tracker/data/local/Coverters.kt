package com.courses.tracker.data.local

import androidx.room.TypeConverter
import com.courses.tracker.domain.model.*

class Converters {

    @TypeConverter
    fun fromHashmapToString(daysHourMinute: Map<DayOfWeek, Pair<Hour, Minute>>): String {
        return fromHourMinuteHashmapToString(daysHourMinute)
    }



    @TypeConverter
    fun stringToHashmap(daysHourMinuteString: String): Map<DayOfWeek, Pair<Hour, Minute>> {
        return stringToHourMinuteHashmap(daysHourMinuteString)
    }
}