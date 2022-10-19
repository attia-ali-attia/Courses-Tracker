package com.courses.tracker.presentation.student_infos

import com.courses.tracker.domain.model.StudentInfo

data class StudentInfosState(
    val students: List<StudentInfo> = emptyList(),
    val isLoading: Boolean = false
)
