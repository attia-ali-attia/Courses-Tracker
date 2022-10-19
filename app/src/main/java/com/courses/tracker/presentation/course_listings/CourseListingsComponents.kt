package com.courses.tracker.presentation.course_listings

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.courses.tracker.R
import com.courses.tracker.domain.model.Course
import com.courses.tracker.domain.model.DayOfWeek
import com.courses.tracker.presentation.destinations.AddEditCourseDialogDestination
import com.courses.tracker.presentation.destinations.StudentInfosScreenDestination
import com.courses.tracker.ui.theme.Shapes
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@ExperimentalMaterialApi
@Composable
internal fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.h5.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    onDeleteCourseClicked: (Course) -> Unit,
    courses: List<Course>,
    shape: Shape = Shapes.medium,
    padding: Dp = 12.dp,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300, easing = LinearOutSlowInEasing
                )
            ),
        shape = shape
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                expandedState = !expandedState
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(padding)
            ) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(modifier = Modifier
                    .weight(1f)
                    .alpha(ContentAlpha.medium)
                    .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                LazyColumn {
                    items(courses) { course ->
                        CourseItem(course = course,
                            indexOfCourse = courses.indexOf(course),
                            onClick = {
                                navigator.navigate(
                                    StudentInfosScreenDestination(course)
                                )
                            },
                            onLongClick = {
                                navigator.navigate(
                                    AddEditCourseDialogDestination(course)
                                )
                            },
                            onDeleteClicked = {
                                onDeleteCourseClicked(course)
                            })
                    }
                }
            }
        }
    }
}

@Destination(style = DestinationStyle.Dialog.Default::class)
@Composable
fun AddEditCourseDialog(
    navigator: DestinationsNavigator,
    course: Course
) {

    val new = course.name.isEmpty()
    val action = if (new) actions?.onCourseInserted else actions?.onCourseUpdated

    var courseName by rememberSaveable { mutableStateOf(course.name) }
    var courseSchedule by rememberSaveable { mutableStateOf(course.scheduleDays.toMutableSet()) }
    var coursePrice by rememberSaveable { mutableStateOf(course.price) }
    var numberOfLessons by rememberSaveable { mutableStateOf(course.numberOfLessons) }
    var numberOfFinishedLessons by rememberSaveable { mutableStateOf(course.numberOfFinishedLessons) }

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = courseName,
                onValueChange = { value ->
                    if (value.isNotEmpty())
                        courseName = value
                },
                label = { Text(stringResource(id = R.string.name)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            LazyRow(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                items(DayOfWeek.values()) { day ->
                    var dayContained by rememberSaveable {
                        mutableStateOf(
                            courseSchedule.contains(
                                day
                            )
                        )
                    }
                    Text(text = day.name.substring(0, 3),
                        color = if (dayContained) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(5.dp)
                            .border(
                                if (dayContained) 0.dp else 1.dp,
                                MaterialTheme.colors.onSecondary,
                                RoundedCornerShape(2000F)
                            )
                            .background(
                                if (dayContained) MaterialTheme.colors.primary
                                else MaterialTheme.colors.onPrimary, RoundedCornerShape(2000F)
                            )
                            .clickable {
                                dayContained = !dayContained
                                if (dayContained) {
                                    courseSchedule.add(day)
                                } else {
                                    courseSchedule.remove(day)
                                }
                            }
                            .padding(7.dp)

                    )
                }

            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    value = if (coursePrice == 0) "" else coursePrice.toString(),
                    onValueChange = { value ->
                        if (value.isNotEmpty()) {
                            coursePrice = value.filter { it.isDigit() }.toIntOrNull() ?: 0
                        } else {
                            coursePrice = 0
                        }
                    },
                    readOnly = !new,
                    label = { Text(stringResource(id = R.string.price)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .weight(1F)
                        .padding(horizontal = 5.dp)
                )

                OutlinedTextField(
                    value = numberOfLessons.toString(),
                    onValueChange = { value ->
                        if (value.isNotEmpty()) {
                            numberOfLessons = value.filter { it.isDigit() }.toIntOrNull() ?: 1
                        }
                    },
                    readOnly = !new,
                    label = { Text(stringResource(id = R.string.enter_number_of_lessons)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .weight(1F)
                        .padding(horizontal = 5.dp)
                )

                Column(
                    Modifier.weight(1.5F),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.number_of_finished_lessons, numberOfFinishedLessons
                        ), textAlign = TextAlign.Center
                    )
                    Icon(painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                if (numberOfFinishedLessons < numberOfLessons && numberOfFinishedLessons == course.numberOfFinishedLessons) numberOfFinishedLessons++
                            })
                }

            }

            Row(
                Modifier.align(Alignment.End), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = { actions?.onCancelClicked?.invoke() }) {
                    Text(
                        text = stringResource(id = R.string.cancel)
                    )
                }

                val toast = Toast.makeText(
                    LocalContext.current,
                    stringResource(R.string.fill_empty_fields),
                    Toast.LENGTH_SHORT
                )
                TextButton(
                    onClick = {
                        if (courseName.isNotEmpty() && courseSchedule.isNotEmpty() &&
                            coursePrice != 0 && numberOfLessons != 0
                        ) {
                            action?.invoke(
                                Course(
                                    courseName,
                                    courseSchedule,
                                    coursePrice,
                                    numberOfLessons,
                                    numberOfFinishedLessons,
                                    course.id
                                )
                            )
                        } else
                            toast.show()
                    }
                ) {
                    Text(
                        text = if (new) stringResource(id = R.string.insert) else stringResource(id = R.string.update)
                    )
                }
            }
        }
    }
}

internal data class AddInsertCourseDialogActions(
    val onCourseInserted: (Course) -> Unit,
    val onCourseUpdated: (Course) -> Unit,
    val onCancelClicked: () -> Unit
)

internal var actions: AddInsertCourseDialogActions? = null

internal val emptyCourse = Course("", emptySet(), 0, 0, 0)
