package org.the_chance.honeymart.data.source.local

interface AppDataStorePreferences {
    suspend fun saveThemeState(isDark: Boolean)
     fun getThemeState(): Boolean
    suspend fun clearThemeState()
}