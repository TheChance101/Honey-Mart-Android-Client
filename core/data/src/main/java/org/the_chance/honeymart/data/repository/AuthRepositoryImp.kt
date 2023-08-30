package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.local.AuthorizationPreferences
import org.the_chance.honeymart.data.source.remote.mapper.toAdminLogin
import org.the_chance.honeymart.data.source.remote.mapper.toOwnerFields
import org.the_chance.honeymart.data.source.remote.mapper.toOwnerProfile
import org.the_chance.honeymart.data.source.remote.mapper.toUserLoginFields
import org.the_chance.honeymart.data.source.remote.network.FireBaseMessageService
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.AdminLogin
import org.the_chance.honeymart.domain.model.Owner
import org.the_chance.honeymart.domain.model.OwnerProfile
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.usecase.Tokens
import org.the_chance.honeymart.domain.util.NotFoundException
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthRepositoryImp @Inject constructor(
    private val datastore: AuthorizationPreferences,
    private val honeyMartService: HoneyMartService,
    private val fireBaseMsgService: FireBaseMessageService
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

    override suspend fun getDeviceToken(): String {
        return fireBaseMsgService.getDeviceToken()
    }


    override suspend fun loginUser(
        email: String,
        password: String,
        deviceToken: String
    ): Tokens =
        wrap { honeyMartService.loginUser(email, password, deviceToken) }.value?.toUserLoginFields()
            ?: throw NotFoundException()

    override suspend fun refreshToken(refreshToken: String): Tokens =
        wrap { honeyMartService.refreshToken(refreshToken) }.value?.toUserLoginFields()
            ?: throw NotFoundException()


    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        datastore.saveTokens(accessToken, refreshToken)
    }

    override fun getAccessToken(): String? {
        return datastore.getAccessToken()
    }

    override fun getRefreshToken(): String? {
        return datastore.getRefreshToken()
    }

    override suspend fun clearToken() {
        datastore.clearToken()
    }

    override suspend fun loginOwner(email: String, password: String): Owner {
        return wrap { honeyMartService.loginOwner(email, password) }.value?.toOwnerFields()
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

    override suspend fun getOwnerProfile(): OwnerProfile =
        wrap { honeyMartService.getOwnerProfile() }.value?.toOwnerProfile()
            ?: throw NotFoundException()

    override suspend fun loginAdmin(email: String, password: String): AdminLogin {
        return wrap { honeyMartService.loginAdmin(email, password) }.value?.toAdminLogin()
            ?: throw NotFoundException()
    }

    override suspend fun saveAdminName(name: String) {
        datastore.saveAdminName(name)
    }

    override suspend fun getAdminName(): String? = datastore.getAdminName()

}