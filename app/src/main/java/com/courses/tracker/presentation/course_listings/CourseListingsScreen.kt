package com.courses.tracker.presentation.course_listings

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.courses.tracker.R
import com.courses.tracker.ThemeViewModel
import com.courses.tracker.domain.model.DayOfWeek
import com.courses.tracker.presentation.destinations.AddEditCourseDialogDestination
import com.courses.tracker.util.DataStoreUtil
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
@RootNavGraph(start = true)
@Destination
fun CourseListingsScreen(
    navigator: DestinationsNavigator,
    themeViewModel: ThemeViewModel = hiltViewModel(),
    dataStoreUtil: DataStoreUtil = DataStoreUtil(LocalContext.current.applicationContext),
    viewModel: CourseListingsViewModel = hiltViewModel()
) {
    val state = viewModel.state

    var fabEnabled by remember { mutableStateOf(true) }

    var switchState = themeViewModel.getTheme().collectAsState(initial = false).value
    val coroutineScope = rememberCoroutineScope()

    courseActions = AddInsertCourseDialogActions(onCourseInserted = {
        Log.i("TAG", "CourseListingsScreen: Insert ${it.name}")
        viewModel.onEvent(CourseListingsEvent.OnCourseInserted(it))
        fabEnabled = true
        navigator.popBackStack()
    }, onCourseUpdated = {
        viewModel.onEvent(CourseListingsEvent.OnCourseUpdated(it))
        fabEnabled = true
        navigator.popBackStack()
    }, onCancelClicked = {
        fabEnabled = true
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

    Box {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )


                val darkIcon = painterResource(R.drawable.ic_moon)
                val lightIcon = painterResource(R.drawable.ic_sun)
                Switch(
                    modifier = Modifier.padding(16.dp),
                    checked = switchState,
                    onCheckedChange = {
                        switchState = it

                        coroutineScope.launch(Dispatchers.IO) {
                            dataStoreUtil.saveTheme(it)
                        }
                    },
                    thumbContent = {
                        Icon(
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                            painter = if (switchState) darkIcon else lightIcon,
                            contentDescription = "Dark Mode Switch Icon"
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (state.courses.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.there_is_no_courses),
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




        CompositionLocalProvider(
            LocalRippleTheme provides if (fabEnabled) LocalRippleTheme.current else NoRippleTheme
        ) {
            FloatingActionButton(
                containerColor = if (fabEnabled) FloatingActionButtonDefaults.containerColor else Gray,
                onClick = {
                    if (fabEnabled) {
                        navigator.navigate(
                            AddEditCourseDialogDestination(
                                EMPTY_COURSE
                            )
                        )
                        fabEnabled = false
                    }
                },
                modifier = Modifier
                    .align(BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_add),
                    contentDescription = "Add new course",
                    tint = if (fabEnabled) LocalContentColor.current.copy(alpha = DefaultAlpha)
                    else DarkGray
                )
            }
        }
    }


}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}