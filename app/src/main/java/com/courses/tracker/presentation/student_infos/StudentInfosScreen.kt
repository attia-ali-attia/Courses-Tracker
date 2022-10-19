package com.courses.tracker.presentation.student_infos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.courses.tracker.R
import com.courses.tracker.domain.model.Course
import com.courses.tracker.domain.model.StudentInfo
import com.courses.tracker.presentation.destinations.AddEditStudentInfoDialogDestination
import com.courses.tracker.presentation.student_infos.StudentInfosEvent.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun StudentInfosScreen(
    navigator: DestinationsNavigator,
    course: Course,
    viewModel: StudentInfosViewModel = hiltViewModel()
) {

    actions = AddInsertStudentInfoDialogActions(onStudentInfoInserted = {
        navigator.popBackStack()
        viewModel.onEvent(OnStudentInfoInserted(it))
    }, onStudentInfoUpdated = {
        navigator.popBackStack()
        viewModel.onEvent(OnStudentInfoUpdated(it))
    }, onCancelClicked = {
        navigator.popBackStack()
    })

    val state = viewModel.state
    viewModel.onEvent(OnFetchStudentInfos(course))

    Box {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = course.name,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (state.students.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.there_is_nothing),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    items(
                        state.students.sortedBy { it.name }
                    ) { studentInfo ->
                        StudentInfoItem(
                            studentInfo,
                            onClick = {
                                navigator.navigate(
                                    AddEditStudentInfoDialogDestination(
                                        studentInfo,
                                        course.price
                                    )
                                )
                            },
                            onDeleteIconClick = {
                                viewModel.onEvent(OnStudentInfoDelete(it))
                            }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navigator.navigate(
                    AddEditStudentInfoDialogDestination(
                        StudentInfo("", "", 0, course.id),
                        course.price
                    )
                )
            }, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(painterResource(id = R.drawable.ic_add), contentDescription = "Add new course")
        }
    }
}