package com.courses.tracker.presentation.student_infos

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.courses.tracker.R
import com.courses.tracker.domain.model.StudentInfo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlin.math.abs

@Composable
fun StudentInfoItem(
    studentInfo: StudentInfo,
    onClick: () -> Unit,
    onDeleteIconClick: (StudentInfo) -> Unit
) {
    Card(
        modifier = Modifier.padding(12.dp)
    ) {
        Column(
            Modifier
                .border(1.dp, MaterialTheme.colors.primary)
                .clickable {
                    onClick()
                }
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = studentInfo.name,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
            Row {
                Text(
                    text = stringResource(id = R.string.phone_number) + ": " + studentInfo.phone,
                    modifier = Modifier.weight(1F),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.this_paid, studentInfo.paid),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(.5F)
                )

                studentInfo.dues?.let {
                    if (it < 0) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "He loans",
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            modifier = Modifier.rotate(180F),
                            contentDescription = "He Has",
                            tint = Color.Green
                        )
                    }
                    Text(
                        text = abs(it).toString(),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                }

                var deleteClickTimes by remember {
                    mutableStateOf(0)
                }
                val toast = Toast.makeText(
                    LocalContext.current,
                    stringResource(id = R.string.click_again_to_delete),
                    Toast.LENGTH_SHORT
                )
                Image(
                    painterResource(id = R.drawable.ic_delete),
                    "Delete Course",
                    modifier = Modifier
                        .clickable {
                            if (deleteClickTimes > 0) {
                                onDeleteIconClick(studentInfo)
                                deleteClickTimes = 0
                            } else {
                                deleteClickTimes++
                                Log.i("TAG", "StudentInfoItem: $deleteClickTimes")
                                toast.show()
                            }

                        }
                        .padding(8.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )


            }

        }

    }
}

@Destination(style = DestinationStyle.Dialog.Default::class)
@Composable
fun AddEditStudentInfoDialog(
    studnetInfo: StudentInfo, coursePrice: Int
) {

    val new = studnetInfo.name.isEmpty()
    val action = if (new) actions?.onStudentInfoInserted else actions?.onStudentInfoUpdated

    var studentName by rememberSaveable { mutableStateOf(studnetInfo.name) }
    var studentPhone by rememberSaveable { mutableStateOf(studnetInfo.phone) }
    var studentPaid by rememberSaveable { mutableStateOf(studnetInfo.paid) }

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = studentName,
                onValueChange = { value ->
                    if (value.isNotEmpty()) studentName = value
                },
                readOnly = !new,
                label = { Text(stringResource(id = R.string.name)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween) {

                OutlinedTextField(
                    value = studentPhone,
                    onValueChange = { value ->

                        if (value.isNotEmpty()) {
                            studentPhone = value.filter { it.isDigit() }
                        }
                    },
                    readOnly = !new,
                    isError = studentPhone.isNotEmpty() && studentPhone.length != 10,
                    label = { Text(stringResource(id = R.string.phone_number)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .weight(1F)
                        .padding(end = 5.dp)
                )

                OutlinedTextField(
                    value = if (studentPaid == 0) "" else studentPaid.toString(),
                    onValueChange = { value ->
                        studentPaid = if (value.isNotEmpty()) {
                            value.filter { it.isDigit() }.toIntOrNull() ?: 0
                        } else {
                            0
                        }
                    },
                    isError = studentPaid > coursePrice,
                    label = { Text(stringResource(id = R.string.paid)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 5.dp)
                )
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
                TextButton(onClick = {
                    if (studentName.isNotEmpty() && studentPhone.isNotEmpty() && studentPhone.length == 10 && studentPhone.startsWith(
                            "05"
                        ) && studentPaid <= coursePrice

                    ) {
                        action?.invoke(
                            StudentInfo(
                                studentName,
                                studentPhone,
                                studentPaid,
                                courseId = studnetInfo.courseId
                            )
                        )
                    } else toast.show()
                }) {
                    Text(
                        text = if (new) stringResource(id = R.string.insert) else stringResource(id = R.string.update)
                    )
                }
            }
        }
    }
}

internal data class AddInsertStudentInfoDialogActions(
    val onStudentInfoInserted: (StudentInfo) -> Unit,
    val onStudentInfoUpdated: (StudentInfo) -> Unit,
    val onCancelClicked: () -> Unit
)


internal var actions: AddInsertStudentInfoDialogActions? = null
