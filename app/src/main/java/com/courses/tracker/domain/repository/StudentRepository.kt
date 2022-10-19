package com.courses.tracker.domain.repository

import com.courses.tracker.domain.model.StudentInfo
import kotlinx.coroutines.flow.Flow

interface StudentRepository {

    suspend fun insertStudentInfo(studentInfo: StudentInfo)

    suspend fun updateStudentInfo(studentInfo: StudentInfo)

    suspend fun getStudentsInCourse(params: GetStudentsParameters): Flow<List<StudentInfo>>

    suspend fun deleteAStudent(studentInfo: StudentInfo)
}

data class GetStudentsParameters(
    val courseId: Int,
    val courseLessons: Int,
    val courseFinishedLessons: Int,
    val coursePrice: Int
)