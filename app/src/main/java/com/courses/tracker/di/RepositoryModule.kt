package com.courses.tracker.di

import com.courses.tracker.data.repository.CourseRepositoryImpl
import com.courses.tracker.data.repository.StudentRepositoryImpl
import com.courses.tracker.domain.repository.CourseRepository
import com.courses.tracker.domain.repository.StudentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStudentRepository(
        studentRepositoryImpl: StudentRepositoryImpl
    ): StudentRepository

    @Binds
    @Singleton
    abstract fun bindCourseRepository(
        courseRepositoryImpl: CourseRepositoryImpl
    ): CourseRepository

}