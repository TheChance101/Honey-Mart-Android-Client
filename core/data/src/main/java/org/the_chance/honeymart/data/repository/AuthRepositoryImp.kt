package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.data.source.remote.mapper.toOwnerLoginEntity
import org.the_chance.honeymart.data.source.remote.mapper.toOwnerProfileEntity
import org.the_chance.honeymart.data.source.remote.mapper.toUserLoginEntity
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.OwnerLoginEntity
import org.the_chance.honeymart.domain.model.OwnerProfileEntity
import org.the_chance.honeymart.domain.model.UserLoginEntity
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.NotFoundException
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthRepositoryImp @Inject constructor(
    private val datastore: AuthDataStorePreferences,
    private val honeyMartService: HoneyMartService,
) : BaseRepository(), AuthRepository {

    override suspend fun createOwnerAccount(
        fullName: String, email: String, password: String,
    ): Boolean =
        wrap { honeyMartService.addOwner(fullName, email, password) }.isSuccess
    override suspend fun createUserAccount(
        fullName: String,
        password: String,
        email: String,
    ): Boolean =
        wrap { honeyMartService.addUser(fullName, password, email) }.isSuccess


    override suspend fun loginUser(email: String, password: String): UserLoginEntity =
        wrap { honeyMartService.loginUser(email, password) }.value?.toUserLoginEntity()
            ?: throw NotFoundException()

    override suspend fun saveToken(token: String) {
        datastore.saveToken(token)
        Log.e("Saved Successfully : ", token)
    }

    override fun getToken(): String? {
        return datastore.getToken()
    }

    override suspend fun clearToken() {
        datastore.clearToken()
    }

    override suspend fun loginOwner(email: String, password: String): OwnerLoginEntity {
        return wrap { honeyMartService.loginOwner(email, password) }.value?.toOwnerLoginEntity()
            ?: throw NotFoundException()
    }

    override suspend fun saveOwnerName(name: String) {
        datastore.saveOwnerName(name)
    }

    override fun getOwnerName(): String? = datastore.getOwnerName()

    override suspend fun saveOwnerImageUrl(imageUrl: String) {
        datastore.saveOwnerImageUrl(imageUrl)
    }

    override fun getOwnerImageUrl(): String? = datastore.getOwnerImageUrl()

    override suspend fun getOwnerProfile(): OwnerProfileEntity =
        wrap { honeyMartService.getOwnerProfile() }.value?.toOwnerProfileEntity()
            ?: throw NotFoundException()

}