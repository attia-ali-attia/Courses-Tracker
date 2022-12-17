package com.courses.tracker.presentation.course_listings

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance

@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5