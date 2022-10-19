package com.courses.tracker.di

import android.app.Application
import androidx.room.Room
import com.courses.tracker.data.local.CourseDao
import com.courses.tracker.data.local.CourseTrackerDatabase
import com.courses.tracker.data.local.StudentInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCourseTrackerDatabase(app: Application): CourseTrackerDatabase {
        return Room.databaseBuilder(
            app,
            CourseTrackerDatabase::class.java,
            "course_tracker.db"
        ).build()
    }

    @Provides
    fun provideStudentDao(db: CourseTrackerDatabase): StudentInfoDao {
        return db.studentDao
    }

    @Provides
    fun provideCourseDao(db: CourseTrackerDatabase): CourseDao {
        return db.courseDao
    }


}