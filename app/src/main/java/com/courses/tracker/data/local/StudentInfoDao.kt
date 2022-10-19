package com.courses.tracker.data.local

import androidx.room.Dao
import androidx.room.Query
import com.courses.tracker.data.local.entity.StudentInfoEntity
import com.courses.tracker.util.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentInfoDao : BaseDao<StudentInfoEntity> {
    @Query(
        """
        SELECT *
        FROM student_info
        WHERE course_id = :courseId
    """
    )
    fun getStudentsInCourse(courseId: Int): Flow<List<StudentInfoEntity>>
}