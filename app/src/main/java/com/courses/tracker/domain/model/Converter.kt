package com.courses.tracker.domain.model

fun fromHourMinuteHashmapToString(daysHourMinute: Map<DayOfWeek, Pair<Hour, Minute>>): String {
    val stringBuilder = StringBuilder("")
    daysHourMinute.entries.forEach {
        stringBuilder.append("${it.key.ordinal},${it.value.first.field},${it.value.second.field}.")
    }
    if (stringBuilder.isNotEmpty())
        stringBuilder.deleteCharAt(stringBuilder.lastIndex)
    return stringBuilder.toString()
}


fun stringToHourMinuteHashmap(daysHourMinuteString: String): Map<DayOfWeek, Pair<Hour, Minute>> {
    val hashMap = mutableMapOf<DayOfWeek, Pair<Hour, Minute>>()

    daysHourMinuteString.split(".").forEach { entry ->
        val daysHourMinute = entry.split(",")
        val dayOrdinal = daysHourMinute[0].toInt()
        val day = enumValues<DayOfWeek>()[dayOrdinal]
        val hour = Hour(daysHourMinute[1].toInt())
        val minute = Minute(daysHourMinute[2].toInt())
        hashMap[day] = Pair(hour, minute)
    }
    return hashMap.toMap()
}