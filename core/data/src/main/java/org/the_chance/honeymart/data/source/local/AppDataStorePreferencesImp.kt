package org.the_chance.honeymart.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AppDataStorePreferencesImp @Inject constructor(context: Context) : AppDataStorePreferences {

    companion object {
        private const val PREFERENCES_FILE_NAME = "honey_mart"
        private val KEY_THEME = booleanPreferencesKey("theme")
    }

    private val Context.preferencesDataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
        PREFERENCES_FILE_NAME
    )
    private val prefDataStore = context.preferencesDataStore


    override suspend fun saveThemeState(isDark: Boolean)  {
        prefDataStore.edit { preferences ->
            preferences[KEY_THEME] = isDark
        }
    }

    override suspend fun getThemeState(): Boolean {
        return runBlocking {
            prefDataStore.data.map { preferences -> preferences[KEY_THEME] }.first()?:false
        }
    }

    override suspend fun clearThemeState() {
        prefDataStore.edit { preferences ->
            preferences.remove(KEY_THEME)
        }
    }
}