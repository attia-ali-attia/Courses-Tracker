package com.courses.tracker.presentation.course_listings

import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.courses.tracker.domain.repository.CourseRepository
import com.courses.tracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseListingsViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    var state by mutableStateOf(CourseListingsState())


    private var searchJob: Job? = null

    init {
        getCourseListings()
    }

    fun onEvent(event: CourseListingsEvent) {
        when (event) {
            is CourseListingsEvent.OnCourseInserted -> {
                searchJob?.cancel()
                viewModelScope.launch {
                    repository.insertCourse(event.course)
                }
            }
            is CourseListingsEvent.OnCourseUpdated -> {
                searchJob?.cancel()
                viewModelScope.launch {
                    repository.updateCourse(event.course)
                }
            }
            is CourseListingsEvent.OnCourseDelete -> {
                searchJob?.cancel()
                viewModelScope.launch {
                    repository.deleteACourse(event.course)
                }
            }
            is CourseListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    getCourseListings(event.query)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun getCourseListings(
        query: String = state.searchQuery.lowercase()
    ) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            repository.getCourseListings(query).debounce(250).distinctUntilChanged()
                .collect { result ->
                    state = state.copy(isLoading = false)
                    result.let { listings ->
                        state = state.copy(
                            courses = listings
                        )
                    }
                }
        }
    }

}
