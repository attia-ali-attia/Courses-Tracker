package com.courses.tracker.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.courses.tracker.R

val Cairo = FontFamily(
    Font(R.font.cairo_black, FontWeight.Black),
    Font(R.font.cairo_bold, FontWeight.Bold),
    Font(R.font.cairo_extrabold, FontWeight.ExtraBold),
    Font(R.font.cairo_light, FontWeight.Light),
    Font(R.font.cairo_medium, FontWeight.Medium),
    Font(R.font.cairo_regular, FontWeight.Normal),
    Font(R.font.cairo_semibold, FontWeight.SemiBold),

)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp
    ),
    h5 = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    )
/* Other default text styles to override
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)