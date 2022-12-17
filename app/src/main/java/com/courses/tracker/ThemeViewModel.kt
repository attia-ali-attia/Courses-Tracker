package com.courses.tracker

import android.content.Context
import androidx.lifecycle.ViewModel
import com.courses.tracker.util.DataStoreUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(@ApplicationContext appContext: Context): ViewModel() {

    private val dataStoreUtil = DataStoreUtil(appContext)


    fun getTheme(): Flow<Boolean> {
        return dataStoreUtil.getTheme(false)
    }
}