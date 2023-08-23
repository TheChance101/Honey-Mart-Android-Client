package org.the_chance.honeymart.data.source.local

import kotlinx.coroutines.flow.Flow

interface AppDataStorePreferences {
    suspend fun saveThemeState(isDark: Boolean)
    suspend fun getThemeState(): Boolean
    suspend fun clearThemeState()
}