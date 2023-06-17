package org.the_chance.honeymart.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthDataStorePref @Inject constructor(context: Context) {

    companion object {
        private const val PREFERENCES_FILE_NAME = "honey_mart"
        private val KEY_TOKEN = stringPreferencesKey("token")
    }

    private val Context.preferencesDataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
        PREFERENCES_FILE_NAME
    )
    private val prefDataStore = context.preferencesDataStore


    suspend fun saveToken(token: String) {
        prefDataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
        }
    }

    val getToken: Flow<String?>
        get() = prefDataStore.data.map { preferences ->
            preferences[KEY_TOKEN]
        }

    suspend fun clearToken() {
        prefDataStore.edit { preferences ->
            preferences.remove(KEY_TOKEN)
        }
    }
}