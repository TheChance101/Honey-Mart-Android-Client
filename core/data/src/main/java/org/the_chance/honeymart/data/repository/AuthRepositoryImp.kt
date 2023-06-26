package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.AlreadyExistException
import org.the_chance.honeymart.domain.util.EmailIsExistException
import org.the_chance.honeymart.domain.util.ForbiddenException
import org.the_chance.honeymart.domain.util.InternalServerException
import org.the_chance.honeymart.domain.util.InvalidDataException
import org.the_chance.honeymart.domain.util.NotFoundException
import org.the_chance.honeymart.domain.util.UnAuthorizedException
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
    private val datastore: AuthDataStorePref,
) : AuthRepository {

    override suspend fun createUserAccount(
        fullName: String,
        password: String,
        email: String,
    ): String =
        wrap { honeyMartService.addUser(fullName, password, email) }


    override suspend fun loginUser(email: String, password: String): String =
        wrap { honeyMartService.loginUser(email, password) }.toString()

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

    private suspend fun <T> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
        val response = function()
        return if (response.isSuccessful) {
            if (response.body()?.isSuccess == true) {
                response.body()?.value as T
            } else {
                when (response.body()?.status?.code) {
                    400 -> throw InvalidDataException()
                    403 -> throw ForbiddenException()
                    404 -> throw NotFoundException()
                    409 -> throw AlreadyExistException()
                    500 -> throw InternalServerException()
                    1001 -> throw EmailIsExistException()
                    else -> throw Exception(response.message())
                }
            }
        } else {
            when (response.code()) {
                401 -> throw UnAuthorizedException()
                else -> throw Exception(response.message())
            }
        }
    }

}