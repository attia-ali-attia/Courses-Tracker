package com.courses.tracker.data.mapper

import com.courses.tracker.data.local.entity.CourseEntity
import com.courses.tracker.domain.model.Course

fun mapCourse(input: Course): CourseEntity {

    return CourseEntity(
        input.id,
        input.name,
        input.daysHourMinute,
        input.price,
        input.numberOfLessons,
        input.numberOfFinishedLessons
    )
}

fun mapCourseEntity(input: CourseEntity): Course {

    return Course(
        input.name,
        input.daysHourMinute,
        input.price,
        input.numberOfLessons,
        input.numberOfFinishedLessons,
        input.id
    )
}
