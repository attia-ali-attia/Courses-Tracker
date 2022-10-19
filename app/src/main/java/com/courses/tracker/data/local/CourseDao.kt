package com.courses.tracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.courses.tracker.data.local.entity.CourseEntity
import com.courses.tracker.util.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao : BaseDao<CourseEntity> {

    @Query(
        """
        SELECT *
        FROM course_info
        WHERE LOWER(course_name) LIKE '%' || LOWER(:query) || '%' 
    """
    )
    fun searchCourseOrStudent(query: String): Flow<List<CourseEntity>>

}