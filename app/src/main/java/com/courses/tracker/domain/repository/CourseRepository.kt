package com.courses.tracker.domain.repository

import com.courses.tracker.domain.model.Course
import com.courses.tracker.util.Resource
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    suspend fun insertCourse(course: Course)

    suspend fun updateCourse(course: Course)

    suspend fun getCourseListings(
        query: String
    ): Flow<List<Course>>

    suspend fun deleteACourse(course: Course)
}