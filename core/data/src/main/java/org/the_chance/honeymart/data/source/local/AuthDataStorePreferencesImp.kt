package org.the_chance.honeymart.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthDataStorePreferencesImp @Inject constructor(context: Context) : AuthorizationPreferences {

    companion object {
        private const val PREFERENCES_FILE_NAME = "honey_mart"
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val OWNER_NAME = stringPreferencesKey("owner_name")
        private val OWNER_IMAGE = stringPreferencesKey("owner_image")
        private val ADMIN_NAME = stringPreferencesKey("admin_name")
        private val OWNER_MARKET_ID = longPreferencesKey("owner_marketId")
    }

    override var storedAccessToken: String? = null
    override var storedRefreshToken: String? = null

    private val Context.preferencesDataStore: DataStore<
            androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
        PREFERENCES_FILE_NAME
    )
    private val prefDataStore = context.preferencesDataStore

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        prefDataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = accessToken
            preferences[KEY_REFRESH_TOKEN] = refreshToken
        }
    }

    override fun getAccessToken(): String? {
        return runBlocking {
            storedAccessToken =
                prefDataStore.data.map { preferences -> preferences[KEY_ACCESS_TOKEN] }.first()
            storedAccessToken
        }
    }

    override fun getRefreshToken(): String? {
        return runBlocking {
            storedRefreshToken =
                prefDataStore.data.map { preferences -> preferences[KEY_REFRESH_TOKEN] }.first()
            storedRefreshToken
        }
    }

    override suspend fun clearToken() {
        storedAccessToken = null
        storedRefreshToken = null
        prefDataStore.edit { preferences ->
            preferences.remove(KEY_ACCESS_TOKEN)
            preferences.remove(KEY_REFRESH_TOKEN)
        }
    }

    override suspend fun saveOwnerName(name: String) {
        prefDataStore.edit { preferences ->
            preferences[OWNER_NAME] = name
        }
    }

    override fun getOwnerName(): String? {
        return runBlocking {
            prefDataStore.data.map { preferences ->
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
            prefDataStore.data.map { preferences ->
                preferences[OWNER_IMAGE]
            }.first()
        }
    }

    override suspend fun saveOwnerMarketId(marketId: Long) {
        prefDataStore.edit { preferences ->
            preferences[OWNER_MARKET_ID] = marketId

        }
    }

    override fun getOwnerMarketId(): Long? {
        return runBlocking {
            prefDataStore.data.map { preferences ->
                preferences[OWNER_MARKET_ID]
            }.first()
        }
    }

    override suspend fun saveAdminName(name: String) {
        prefDataStore.edit { preferences ->
            preferences[ADMIN_NAME] = name
        }
    }

    override fun getAdminName(): String? {
        return runBlocking {
            prefDataStore.data.map { preferences ->
                preferences[ADMIN_NAME]
            }.first()
        }
    }
}