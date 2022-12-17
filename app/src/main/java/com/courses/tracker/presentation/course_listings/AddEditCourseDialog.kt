package com.courses.tracker.presentation.course_listings


import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.courses.tracker.R
import com.courses.tracker.domain.model.Course
import com.courses.tracker.domain.model.DayOfWeek
import com.courses.tracker.util.DAY_ITEM_CORNERS_SHAPE
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal val EMPTY_COURSE = Course("", HashMap(), 0, 0, 0)

@OptIn(ExperimentalMaterial3Api::class)
@Destination(style = AddEditDialogDestinationStyle::class)
@Composable
fun AddEditCourseDialog(
    navigator: DestinationsNavigator, course: Course
) {

    val new = course.name.isEmpty()
    val action = if (new) courseActions?.onCourseInserted else courseActions?.onCourseUpdated

    var courseName by rememberSaveable { mutableStateOf(course.name) }
    val courseSchedule by rememberSaveable { mutableStateOf(course.scheduleDays) }
    var coursePrice by rememberSaveable { mutableStateOf(course.price) }
    var numberOfLessons by rememberSaveable { mutableStateOf(course.numberOfLessons) }
    var numberOfFinishedLessons by rememberSaveable { mutableStateOf(course.numberOfFinishedLessons) }

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = courseName,
                onValueChange = { value ->
                    if (value.isNotEmpty()) courseName = value
                },
                label = { Text(stringResource(id = R.string.name)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            )

            DaysRow(courseSchedule = courseSchedule)

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    value = if (coursePrice == 0) "" else coursePrice.toString(),
                    onValueChange = { value ->
                        coursePrice = if (value.isNotEmpty()) {
                            value.filter { it.isDigit() }.toIntOrNull() ?: 0
                        } else {
                            0
                        }
                    },
                    readOnly = !new,
                    label = { Text(stringResource(id = R.string.price)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
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
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .weight(1.5F)
                        .padding(horizontal = 5.dp)
                )

                val enterNumberOfLessonsToast = Toast.makeText(
                    LocalContext.current,
                    stringResource(id = R.string.please_enter_lessons_number_first),
                    Toast.LENGTH_LONG
                )
                NumberOfFinishedLessonsItem(Modifier.weight(1.5F), numberOfFinishedLessons) {
                    if (numberOfFinishedLessons < numberOfLessons
                        && numberOfFinishedLessons == course.numberOfFinishedLessons
                    )
                        numberOfFinishedLessons++
                    else if (numberOfLessons == 0) {
                        enterNumberOfLessonsToast.show()
                    }
                }

            }

            val canSubmit = courseName.isNotEmpty() && courseSchedule.isNotEmpty() &&
                    coursePrice != 0 && numberOfLessons != 0
            ActionButtons(Modifier.align(Alignment.End), canSubmit, new) {
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
            }

        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DaysRow(courseSchedule: HashMap<DayOfWeek, String>) {
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

            val hourAndMinute = courseSchedule[day]?.split(":")
            val pickerDialog = timePickerDialog(
                hourAndMinute?.firstOrNull()?.toIntOrNull(),
                hourAndMinute?.get(1)?.toIntOrNull()
            ) {
                courseSchedule[day] = it
                dayContained = true
            }
            Text(
                text = day.name.substring(0, 3),
                color = if (dayContained) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(5.dp)
                    .border(
                        if (dayContained) 0.dp else 1.dp,
                        MaterialTheme.colorScheme.onSecondary,
                        RoundedCornerShape(DAY_ITEM_CORNERS_SHAPE)
                    )
                    .background(
                        if (dayContained) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(DAY_ITEM_CORNERS_SHAPE)
                    )
                    .combinedClickable(onLongClick = {
                        pickerDialog.show()
                    }, onClick = {
                        if (dayContained) {
                            courseSchedule.remove(day)
                            dayContained = !dayContained
                        }
                    })
                    .padding(7.dp)

            )
        }

    }
}

@Composable
private fun NumberOfFinishedLessonsItem(modifier: Modifier, numberOfFinishedLessons: Int, onClick: () -> Unit) {
    Column(
        modifier,
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
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(5.dp)
                .clickable {
                    onClick()
                })
    }
}


@Composable
private fun ActionButtons(modifier: Modifier, canSubmit: Boolean, newCourse: Boolean, onClick: () -> Unit) {
    Row(
        modifier, horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = { courseActions?.onCancelClicked?.invoke() }) {
            Text(
                text = stringResource(id = R.string.cancel)
            )
        }

        val toast = Toast.makeText(
            LocalContext.current,
            stringResource(R.string.fill_empty_fields),
            Toast.LENGTH_SHORT
        )

        TextButton(onClick = {
            if (canSubmit) {
                onClick()
            } else toast.show()
        }) {
            Text(
                text = if (newCourse) stringResource(id = R.string.insert)
                else stringResource(id = R.string.update)
            )
        }
    }
}