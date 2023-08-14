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
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val OWNER_NAME = stringPreferencesKey("owner_name")
        private val OWNER_IMAGE = stringPreferencesKey("owner_image")
    }

    private val Context.preferencesDataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
        PREFERENCES_FILE_NAME
    )
    private val prefDataStore = context.preferencesDataStore


    override suspend fun saveToken(token: String) {
        prefDataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
        }
    }

    override fun getToken(): String? {
        return runBlocking {
            prefDataStore.data.map { preferences -> preferences[KEY_TOKEN] }.first()
        }
    }

    override suspend fun clearToken() {
        prefDataStore.edit { preferences ->
            preferences.remove(KEY_TOKEN)
        }
    }

    override suspend fun saveOwnerName(name: String) {
        prefDataStore.edit { preferences ->
            preferences[OWNER_NAME] = name
        }
    }

    override fun getOwnerName(): String? {
        return runBlocking {
            prefDataStore.data.map { preferences
                ->
                preferences[OWNER_NAME]
            }.first()
        }
    }

    override suspend fun saveOwnerImageUrl(image: String) {
        prefDataStore.edit { preferences ->
            preferences[OWNER_IMAGE] = image
        }
    }

    override fun getOwnerImageUrl(): String? {
        return runBlocking {
            prefDataStore.data.map { preferences
                ->
                preferences[OWNER_IMAGE]
            }.first()
        }
    }
}