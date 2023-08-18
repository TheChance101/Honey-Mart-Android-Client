package org.the_chance.honeymart.domain.repository

import org.the_chance.honeymart.domain.model.OwnerLoginEntity
import org.the_chance.honeymart.domain.model.OwnerProfileEntity

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
interface AuthRepository {
    suspend fun loginUser(email: String, password: String): String

    suspend fun saveToken(token: String)
    fun getToken(): String?
    suspend fun clearToken()

    suspend fun createUserAccount(fullName: String, password: String, email: String): Boolean

    suspend fun createOwnerAccount(fullName: String, email: String, password: String): Boolean

    suspend fun loginOwner(email: String, password: String): OwnerLoginEntity

    suspend fun saveOwnerName(name: String)
    fun getOwnerName(): String?
    suspend fun saveOwnerImageUrl(imageUrl: String)
    fun getOwnerImageUrl(): String?
    suspend fun getOwnerProfile(): OwnerProfileEntity
}