package com.courses.tracker.domain.model


import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

@Parcelize
@TypeParceler<Map<DayOfWeek, Pair<Hour, Minute>>, HourMinutePairParceler>()
data class Course(
    val name: String,
    val daysHourMinute: Map<DayOfWeek, Pair<Hour, Minute>>,
    val price: Int,
    val numberOfLessons: Int,
    val numberOfFinishedLessons: Int,
    val id: Int = 0
) : Parcelable

object HourMinutePairParceler : Parceler<Map<DayOfWeek, Pair<Hour, Minute>>> {
    override fun create(parcel: Parcel): Map<DayOfWeek, Pair<Hour, Minute>> {
        val stringtoMap = parcel.readString() ?: ""
        return if (stringtoMap.isNotEmpty())
            stringToHourMinuteHashmap(stringtoMap)
        else
            emptyMap()
    }

    override fun Map<DayOfWeek, Pair<Hour, Minute>>.write(parcel: Parcel, flags: Int) {
        if (this.isNotEmpty())
            parcel.writeString(fromHourMinuteHashmapToString(this))
    }
}