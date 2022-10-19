package com.courses.tracker.presentation.course_listings

import com.courses.tracker.domain.model.Course

sealed class CourseListingsEvent {
    data class OnCourseUpdated(val course: Course) : CourseListingsEvent()
    data class OnCourseDelete(val course: Course) : CourseListingsEvent()
    data class OnCourseInserted(val course: Course) : CourseListingsEvent()
    data class OnSearchQueryChange(val query: String): CourseListingsEvent()
}