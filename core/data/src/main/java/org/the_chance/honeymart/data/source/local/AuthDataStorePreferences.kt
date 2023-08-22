package org.the_chance.honeymart.data.source.local

interface AuthDataStorePreferences {
    suspend fun clearToken()
    suspend fun saveTokens(accessToken: String,refreshToken: String)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
}