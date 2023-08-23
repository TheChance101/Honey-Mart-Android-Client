package org.the_chance.honeymart.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthDataStorePreferencesImp @Inject constructor(context: Context) : AuthDataStorePreferences {

    companion object {
        private const val PREFERENCES_FILE_NAME = "honey_mart"
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    private val Context.preferencesDataStore: DataStore<
            androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
        PREFERENCES_FILE_NAME
    )
    private val prefDataStore = context.preferencesDataStore

    override suspend fun saveTokens(accessToken: String,refreshToken: String) {
        prefDataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = accessToken
            preferences[KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    override fun getAccessToken(): String? {
        return runBlocking {
            prefDataStore.data.map { preferences -> preferences[KEY_ACCESS_TOKEN] }.first()
        }
    }

    override fun getRefreshToken(): String? {
        return runBlocking {
            prefDataStore.data.map { preferences -> preferences[KEY_REFRESH_TOKEN] }.first()
        }
    }

    override suspend fun clearToken() {
        prefDataStore.edit { preferences ->
            preferences.remove(KEY_ACCESS_TOKEN)
            preferences.remove(KEY_REFRESH_TOKEN)
        }
    }
}