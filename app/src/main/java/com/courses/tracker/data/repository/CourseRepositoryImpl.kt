package com.courses.tracker.data.repository

import com.courses.tracker.data.local.CourseDao
import com.courses.tracker.data.local.StudentInfoDao
import com.courses.tracker.data.mapper.mapCourse
import com.courses.tracker.data.mapper.mapCourseEntity
import com.courses.tracker.domain.model.Course
import com.courses.tracker.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao,
    private val studentInfoDao: StudentInfoDao
) : CourseRepository {

    override suspend fun insertCourse(course: Course) {
        courseDao.insert(mapCourse(course))
    }

    override suspend fun updateCourse(course: Course) {
        courseDao.update(mapCourse(course))
    }

    override suspend fun getCourseListings(query: String): Flow<List<Course>> {
        return courseDao.searchCourseOrStudent(query).map { list ->
            list.map { mapCourseEntity(it) }
        }
    }

    override suspend fun deleteACourse(course: Course) {
        courseDao.delete(mapCourse(course))
        studentInfoDao.deleteAllStudentsInCourse(course.id)
    }
}