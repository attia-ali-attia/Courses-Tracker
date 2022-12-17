package com.courses.tracker.domain.model

import com.courses.tracker.util.HOURS_IN_DAY
import com.courses.tracker.util.MINUTES_IN_HOUR

@JvmInline
value class Hour(val field: Int) {
    init {
        if (field>HOURS_IN_DAY || field<0) {
            throw IllegalArgumentException("Hour is wrong $field")
        }
    }
}
@JvmInline
value class Minute(val field: Int) {
    init {
        if (field>MINUTES_IN_HOUR || field<0) {
            throw IllegalArgumentException("Minute is wrong $field")
        }
    }
}