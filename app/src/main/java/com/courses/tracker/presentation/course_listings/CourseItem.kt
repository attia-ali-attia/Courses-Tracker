package com.courses.tracker.presentation.course_listings


import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.courses.tracker.R
import com.courses.tracker.domain.model.Course

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CourseItem(
    modifier: Modifier = Modifier,
    course: Course,
    indexOfCourse: Int,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onDeleteClicked: () -> Unit,
    priceColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.onSecondary,
    fontWeight: FontWeight = FontWeight.SemiBold,
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
) {
    val backgroundColors =
        listOf("CDF0EA", "F9F9F9", "ECC5FB", "FAF4B7", "B1D7B4", "F29393", "59CE8F")
    Column(
        modifier = modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() },
            )
            .background(getColor(backgroundColors[indexOfCourse % backgroundColors.size]))
            .padding(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = course.name,
                fontWeight = fontWeight,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(6F)
                    .padding(5.dp)
            )

            Text(
                text = course.price.toString(),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                color = priceColor,
                modifier = Modifier
                    .weight(1F)
                    .padding(5.dp)
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(course.scheduleDays.keys.toList()) { day ->
                Text(
                    text = day.name.substring(0, 3) + " " + stringResource(id = R.string.session_time, course.scheduleDays.get(day)!!),
                    fontWeight = fontWeight,
                    fontSize = fontSize,
                    modifier = Modifier
                        .padding(5.dp)
                        .border(1.dp, borderColor, RoundedCornerShape(2000F))
                        .padding(7.dp)
                )
            }

        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.number_of_lessons, course.numberOfLessons),
                fontSize = fontSize
            )
            if (course.numberOfFinishedLessons > 0) {
                Text(
                    text = stringResource(
                        R.string.number_of_finished_lessons, course.numberOfFinishedLessons
                    ), fontSize = fontSize
                )
            }

            var deleteClickTimes = 0
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
                            onDeleteClicked()
                            deleteClickTimes = 0
                        } else {
                            deleteClickTimes++
                            toast.show()
                        }
                    }
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )

        }


    }

}

fun getColor(colorString: String): Color {
    return Color(android.graphics.Color.parseColor("#$colorString"))
}

