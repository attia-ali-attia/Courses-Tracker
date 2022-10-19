package com.courses.tracker.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentInfo(
    val name: String,
    val phone: String,
    val paid: Int,
    val courseId: Int,
    val dues: Int? = null
): Parcelable