package org.the_chance.honeymart.data.source.local

interface AuthDataStorePref {
    suspend fun saveToken(token: String)
    fun getToken(): String?
    suspend fun clearToken()
}