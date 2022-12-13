package com.courses.tracker.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreUtil @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

        val THEME_KEY = booleanPreferencesKey("theme")
    }

    fun getTheme(isSystemDarkTheme: Boolean): Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: isSystemDarkTheme
        }

    suspend fun saveTheme(isDarkThemeEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkThemeEnabled
        }
    }
}