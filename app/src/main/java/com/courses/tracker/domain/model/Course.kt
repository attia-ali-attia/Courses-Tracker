package com.courses.tracker.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    val name: String,
    val scheduleDays: HashMap<DayOfWeek, String>,
    val price: Int,
    val numberOfLessons: Int,
    val numberOfFinishedLessons: Int,
    val id: Int = 0
): Parcelable