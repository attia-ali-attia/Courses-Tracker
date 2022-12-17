package com.courses.tracker.presentation.course_listings

import android.app.TimePickerDialog
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.courses.tracker.domain.model.Course
import com.courses.tracker.domain.model.Hour
import com.courses.tracker.domain.model.Minute
import com.courses.tracker.presentation.destinations.AddEditCourseDialogDestination
import com.courses.tracker.presentation.destinations.StudentInfosScreenDestination
import com.courses.tracker.ui.theme.Shapes
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*
import kotlin.math.absoluteValue

private const val TITLE_WEIGHT = 6f

@Composable
internal fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.headlineMedium.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    onDeleteCourseClicked: (Course) -> Unit,
    courses: List<Course>,
    shape: Shape = Shapes.large,
    padding: Dp = 12.dp,
    navigator: DestinationsNavigator
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 150, easing = LinearOutSlowInEasing
                )
            ), shape = shape
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
                    modifier = Modifier.weight(TITLE_WEIGHT),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.5f)
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
                    items(courses, key = { it.id }) { course ->
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

@Composable
fun timePickerDialog(
    hour: Int? = null, minute: Int? = null, onTimeSetListener: (Hour, Minute) -> Unit
): TimePickerDialog {

    val calendar = Calendar.getInstance()
    val hourNow = calendar[Calendar.HOUR_OF_DAY]
    val minuteNow = calendar[Calendar.MINUTE]

    val hourState = remember { mutableStateOf(0) }
    val minuteState = remember { mutableStateOf(0) }

    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour: Int, minute: Int ->
            hourState.value = if (hour <= 9)  "0$hour".toInt()
                else hour

            minuteState.value = if (minute <= 9) "0$minute".toInt()
            else minute

            onTimeSetListener(Hour(hourState.value), Minute(minuteState.value))
        },
        hour ?: hourNow, minute ?: minuteNow, false,
    )
    timePickerDialog.setCancelable(false)

    return timePickerDialog
}
