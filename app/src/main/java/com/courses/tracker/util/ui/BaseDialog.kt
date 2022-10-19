package com.courses.tracker.util.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.courses.tracker.R


@Composable
fun BaseDialog(
    confirmButtonText: String = stringResource(id = R.string.confirm),
    onConfirmButtonClicked: () -> Unit,
    cancelButtonText: String = stringResource(id = R.string.cancel),
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    
}