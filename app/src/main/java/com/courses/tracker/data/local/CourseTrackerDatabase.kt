package com.courses.tracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.courses.tracker.data.local.entity.CourseEntity
import com.courses.tracker.data.local.entity.StudentInfoEntity

@Database(
    entities = [CourseEntity::class, StudentInfoEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CourseTrackerDatabase : RoomDatabase() {

    abstract val courseDao: CourseDao
    abstract val studentDao: StudentInfoDao

}