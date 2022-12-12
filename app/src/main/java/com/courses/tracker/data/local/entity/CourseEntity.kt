package com.courses.tracker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_info")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "course_name")
    val name: String,
    @ColumnInfo(name="schedule_days")
    val scheduleDays: List<Int>,
    @ColumnInfo(name="schedule_hours")
    val scheduleHours: String,
    val price: Int,
    @ColumnInfo(name="number_of_lessons")
    val numberOfLessons: Int,
    @ColumnInfo(name="number_of_finished_lessons")
    val numberOfFinishedLessons: Int
)