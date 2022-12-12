package com.courses.tracker.data.mapper

import android.util.Log
import com.courses.tracker.data.local.entity.CourseEntity
import com.courses.tracker.domain.model.Course
import com.courses.tracker.domain.model.DayOfWeek

fun mapCourse(input: Course): CourseEntity {
    val scheduleHours = StringBuilder()
    input.scheduleDays.values.forEach {
        scheduleHours.append("$it,")
    }
    scheduleHours.deleteAt(scheduleHours.lastIndex)


    val mappedList = input.scheduleDays.map { it.key.ordinal }.sortedBy {
        it
    }

    Log.i("TAG", "mapCourse: $mappedList")

    return CourseEntity(
        input.id,
        input.name,
        mappedList,
        scheduleHours.toString(),
        input.price,
        input.numberOfLessons,
        input.numberOfFinishedLessons
    )
}

fun mapCourseEntity(input: CourseEntity): Course {
    val daysSet = input.scheduleDays.map {
        DayOfWeek.values()[it]
    }.sortedDescending().toSet()

    val splittedString = input.scheduleHours.split(",")
    val hashMap = HashMap<DayOfWeek, String>()
    daysSet.forEach {
        hashMap[it] = splittedString[it.ordinal]
    }

    Log.i("TAG", "mapCourseEntity: ${hashMap.get(DayOfWeek.SATURDAY)}")


    return Course(
        input.name,
        hashMap,
        input.price,
        input.numberOfLessons,
        input.numberOfFinishedLessons,
        input.id
    )
}
