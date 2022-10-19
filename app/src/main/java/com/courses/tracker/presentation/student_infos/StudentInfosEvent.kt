package com.courses.tracker.presentation.student_infos

import com.courses.tracker.domain.model.Course
import com.courses.tracker.domain.model.StudentInfo

sealed class StudentInfosEvent {
    data class OnStudentInfoUpdated(val studentInfo: StudentInfo) : StudentInfosEvent()
    data class OnStudentInfoDelete(val studentInfo: StudentInfo) : StudentInfosEvent()
    data class OnStudentInfoInserted(val studentInfo: StudentInfo) : StudentInfosEvent()
    data class OnFetchStudentInfos(val course: Course): StudentInfosEvent()
}