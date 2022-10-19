package com.courses.tracker.data.repository

import android.util.Log
import com.courses.tracker.data.local.StudentInfoDao
import com.courses.tracker.data.mapper.mapStudentInfo
import com.courses.tracker.data.mapper.mapStudentInfoEntity
import com.courses.tracker.domain.model.StudentInfo
import com.courses.tracker.domain.repository.GetStudentsParameters
import com.courses.tracker.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val studentInfoDao: StudentInfoDao
) : StudentRepository {

    override suspend fun insertStudentInfo(studentInfo: StudentInfo) {
        studentInfoDao.insert(mapStudentInfo(studentInfo))
    }

    override suspend fun updateStudentInfo(studentInfo: StudentInfo) {
        studentInfoDao.update(mapStudentInfo(studentInfo))
    }

    override suspend fun getStudentsInCourse(params: GetStudentsParameters): Flow<List<StudentInfo>> {
        return studentInfoDao.getStudentsInCourse(params.courseId).map { list ->
            list.map { studentInfo ->
                val studentsDues =
                    studentInfo.paid - (params.courseFinishedLessons * params.coursePrice) / params.courseLessons
                mapStudentInfoEntity(studentInfo, studentsDues)
            }
        }
    }

    override suspend fun deleteAStudent(studentInfo: StudentInfo) {
        studentInfoDao.delete(mapStudentInfo(studentInfo))
    }

}