package com.courses.tracker.presentation.student_infos

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.courses.tracker.domain.repository.GetStudentsParameters
import com.courses.tracker.domain.repository.StudentRepository
import com.courses.tracker.presentation.student_infos.StudentInfosEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentInfosViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    var state by mutableStateOf(StudentInfosState())

    private var searchJob: Job? = null

    fun onEvent(event: StudentInfosEvent) {
        when (event) {
            is OnFetchStudentInfos -> {
                val course = event.course
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    getStudentInfos(
                        GetStudentsParameters(
                            course.id,
                            course.numberOfLessons,
                            course.numberOfFinishedLessons,
                            course.price
                        )
                    )
                }
            }
            is OnStudentInfoInserted -> {
                searchJob?.cancel()
                viewModelScope.launch {
                    repository.insertStudentInfo(event.studentInfo)
                }
            }
            is OnStudentInfoUpdated -> {
                searchJob?.cancel()
                viewModelScope.launch {
                    repository.updateStudentInfo(event.studentInfo)
                }
            }
            is OnStudentInfoDelete -> {
                searchJob?.cancel()
                viewModelScope.launch {
                    repository.deleteAStudent(event.studentInfo)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun getStudentInfos(
        params: GetStudentsParameters
    ) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            repository.getStudentsInCourse(params).debounce(250).distinctUntilChanged()
                .collect { result ->
                    state = state.copy(isLoading = false)
                    result.let { listings ->
                        state = state.copy(
                            students = listings
                        )
                    }
                }
        }
    }
}