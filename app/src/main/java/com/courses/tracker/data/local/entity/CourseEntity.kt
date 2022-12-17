package com.courses.tracker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.courses.tracker.domain.model.DayOfWeek
import com.courses.tracker.domain.model.Hour
import com.courses.tracker.domain.model.Minute

@Entity(tableName = "course_info")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "course_name")
    val name: String,
    @ColumnInfo(name="days_hour_minute")
    val daysHourMinute: Map<DayOfWeek, Pair<Hour, Minute>>,
    val price: Int,
    @ColumnInfo(name="number_of_lessons")
    val numberOfLessons: Int,
    @ColumnInfo(name="number_of_finished_lessons")
    val numberOfFinishedLessons: Int
)