package org.the_chance.honeymart.data.source.local

interface AuthDataStorePreferences {
    suspend fun saveToken(token: String)
    fun getToken(): String?
    suspend fun clearToken()
}