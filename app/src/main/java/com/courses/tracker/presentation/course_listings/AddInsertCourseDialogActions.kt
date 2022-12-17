package com.courses.tracker.presentation.course_listings

import com.courses.tracker.domain.model.Course

internal data class AddInsertCourseDialogActions(
    val onCourseInserted: (Course) -> Unit,
    val onCourseUpdated: (Course) -> Unit,
    val onCancelClicked: () -> Unit
)

internal var courseActions: AddInsertCourseDialogActions? = null