package com.courses.tracker.data.mapper

import com.courses.tracker.data.local.entity.StudentInfoEntity
import com.courses.tracker.domain.model.StudentInfo

fun mapStudentInfo(input: StudentInfo): StudentInfoEntity {
    return StudentInfoEntity(
        input.name,
        input.courseId,
        input.phone,
        input.paid
    )
}

fun mapStudentInfoEntity(input: StudentInfoEntity, dues: Int): StudentInfo =
    StudentInfo(
        input.name,
        input.phone,
        input.paid,
        input.courseId,
        dues
    )