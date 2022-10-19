package com.courses.tracker.data.mapper

import com.courses.tracker.data.local.entity.CourseEntity
import com.courses.tracker.domain.model.Course
import com.courses.tracker.domain.model.DayOfWeek

fun mapCourse(input: Course): CourseEntity {
    return CourseEntity(
        input.id,
        input.name,
        input.scheduleDays.map { it.ordinal },
        input.price,
        input.numberOfLessons,
        input.numberOfFinishedLessons
    )
}

fun mapCourseEntity(input: CourseEntity): Course {
    val daysSet = input.scheduleDays.map {
        DayOfWeek.values()[it]
    }.sortedDescending().toSet()


    return Course(
        input.name,
        daysSet,
        input.price,
        input.numberOfLessons,
        input.numberOfFinishedLessons,
        input.id
    )
}
