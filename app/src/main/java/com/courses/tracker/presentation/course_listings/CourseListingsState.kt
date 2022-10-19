package com.courses.tracker.presentation.course_listings

import com.courses.tracker.domain.model.Course

data class CourseListingsState(
    val courses: List<Course> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)