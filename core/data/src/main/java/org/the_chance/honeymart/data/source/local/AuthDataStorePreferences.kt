package org.the_chance.honeymart.data.source.local

interface AuthDataStorePreferences {
    suspend fun saveToken(token: String)
    fun getToken(): String?
    suspend fun clearToken()

    suspend fun saveProfileName(name:String)
    fun getProfileName(): String?
    suspend fun saveProfileImage(image:String)
    fun getProfileImage(): String?
}