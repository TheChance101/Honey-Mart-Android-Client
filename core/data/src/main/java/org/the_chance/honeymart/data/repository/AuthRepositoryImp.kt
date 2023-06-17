package org.the_chance.honeymart.data.repository

import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
    private val authDataStorePref: AuthDataStorePref
) : AuthRepository {
    override suspend fun loginUser(email: String, password: String): String =
        wrap { honeyMartService.loginUser(email, password) }.toString()

    override suspend fun saveToken(token: String) {
        authDataStorePref.saveToken(token)
    }

    override suspend fun getToken(): Flow<String?> {
        return authDataStorePref.getToken
    }

    override suspend fun clearToke() {
        authDataStorePref.clearToken()
    }

    private suspend fun <T : Any> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
        val response = function()
        return if (response.isSuccessful) {
            response.body()?.value as T
        } else {
            throw Throwable("Unknown error occurred")
        }
    }

}