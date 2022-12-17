package com.courses.tracker.presentation.course_listings

import androidx.compose.ui.window.DialogProperties
import com.ramcosta.composedestinations.spec.DestinationStyle

object AddEditDialogDestinationStyle : DestinationStyle.Dialog {
    override val properties: DialogProperties
        get() = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
}