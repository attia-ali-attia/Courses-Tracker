package com.courses.tracker.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    val name: String,
    val scheduleDays: Set<DayOfWeek>,
    val price: Int,
    val numberOfLessons: Int,
    val numberOfFinishedLessons: Int,
    val id: Int = 0
): Parcelable