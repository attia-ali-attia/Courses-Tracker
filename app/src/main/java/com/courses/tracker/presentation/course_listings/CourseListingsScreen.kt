package com.courses.tracker.presentation.course_listings

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.courses.tracker.R
import com.courses.tracker.domain.model.DayOfWeek
import com.courses.tracker.presentation.destinations.AddEditCourseDialogDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
@RootNavGraph(start = true)
@Destination
fun CourseListingsScreen(
    navigator: DestinationsNavigator, viewModel: CourseListingsViewModel = hiltViewModel()
) {
    courseActions = AddInsertCourseDialogActions(
        onCourseInserted = {
            Log.i("TAG", "CourseListingsScreen: Insert ${it.name}")
            viewModel.onEvent(CourseListingsEvent.OnCourseInserted(it))
            navigator.popBackStack()
        }, onCourseUpdated = {
            viewModel.onEvent(CourseListingsEvent.OnCourseUpdated(it))
            navigator.popBackStack()
        }, onCancelClicked = {
            navigator.popBackStack()
        })

//    LaunchedEffect(true) {
//        val hashMap = hashMapOf<DayOfWeek, String>(
//            Pair(DayOfWeek.SATURDAY, "14:59"),
//            Pair(DayOfWeek.SUNDAY, "05:30"),
//            Pair(DayOfWeek.MONDAY, "12:00"),
//        )
//        viewModel.onEvent(
//            CourseListingsEvent.OnCourseInserted(
//                Course(
//                    "add",
//                    hashMap,
//                    1500,
//                    1,
//                    1
//                )
//            )
//        )
//    }

    val state = viewModel.state
    Box {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (state.courses.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.there_is_nothing),
                    textAlign = TextAlign.Center
                )
            } else {
                Column {
                    for (day in DayOfWeek.values()) {
                        ExpandableCard(title = day.name, courses = state.courses.filter {
                            it.scheduleDays.contains(day)
                        }.sortedBy {
                            it.scheduleDays[day]
                        }, navigator = navigator, onDeleteCourseClicked = {
                            viewModel.onEvent(CourseListingsEvent.OnCourseDelete(it))
                        })
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navigator.navigate(
                    AddEditCourseDialogDestination(
                        emptyCourse
                    )
                )
            }, modifier = Modifier
                .align(BottomEnd)
                .padding(16.dp)
        ) {
            Icon(painterResource(id = R.drawable.ic_add), contentDescription = "Add new course")
        }
    }


}