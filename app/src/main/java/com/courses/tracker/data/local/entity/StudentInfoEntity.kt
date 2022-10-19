package com.courses.tracker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "student_info", primaryKeys = ["course_id", "student_name"])
data class StudentInfoEntity(
    @ColumnInfo(name = "student_name")
    val name: String,
    @ColumnInfo(name = "course_id")
    val courseId: Int,
    val phone: String,
    val paid: Int
)