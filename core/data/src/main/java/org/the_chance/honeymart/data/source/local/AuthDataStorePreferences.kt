package org.the_chance.honeymart.data.source.local

interface AuthDataStorePreferences {
    suspend fun saveToken(token: String)
    fun getToken(): String?
    suspend fun clearToken()
    suspend fun saveOwnerName(name: String)
    fun getOwnerName(): String?
    suspend fun saveOwnerImage(image: String)
    fun getOwnerImage(): String?
}