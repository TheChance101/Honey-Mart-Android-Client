package org.the_chance.honeymart.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.InvalidEmailException
import org.the_chance.honeymart.domain.util.InvalidFullNameException
import org.the_chance.honeymart.domain.util.InvalidPasswordException
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
    private val datastore: AuthDataStorePref,
) : AuthRepository {
    override suspend fun loginUser(email: String, password: String): String =
        wrap { honeyMartService.loginUser(email, password) }.toString()

    override suspend fun saveToken(token: String) {
        datastore.saveToken(token)
        Log.e("Saved Successfully : ", "$token")
    }

    override suspend fun getToken(): Flow<String?> {
        return datastore.getToken()
    }

    override suspend fun clearToken() {
        datastore.clearToken()
    }

    private suspend fun <T : Any> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
        val response = function()
        return if (response.isSuccessful) {
            response.body()?.value as T
        } else {
            when (response.code()) {
                400 -> throw InvalidPasswordException()
                400 -> throw InvalidEmailException()
                400 -> throw InvalidFullNameException()
                else -> throw Exception(response.message())
            }
        }
    }

    override suspend fun addUser(
        fullName: String,
        password: String,
        email: String,
    ): Boolean? {

        val response = honeyMartService.addUser(fullName, password, email)

        return if (response.isSuccessful) {
            response.body()?.isSuccess
        } else {
            when (response.code()) {
                400 -> throw InvalidPasswordException()
                400 -> throw InvalidEmailException()
                400 -> throw InvalidFullNameException()
                else -> throw Exception(response.message())
            }
            false
        }

    }
}