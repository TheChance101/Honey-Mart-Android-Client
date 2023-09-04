package org.the_chance.honeymart.data.repository

import android.util.Log
import io.ktor.client.plugins.ClientRequestException
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.domain.util.AlreadyExistException
import org.the_chance.honeymart.domain.util.EmailIsExistException
import org.the_chance.honeymart.domain.util.ForbiddenException
import org.the_chance.honeymart.domain.util.InternalServerException
import org.the_chance.honeymart.domain.util.InvalidDataException
import org.the_chance.honeymart.domain.util.MarketDeletedException
import org.the_chance.honeymart.domain.util.NotFoundException
import org.the_chance.honeymart.domain.util.NotValidApiKeyException
import org.the_chance.honeymart.domain.util.UnAuthorizedCredential
import org.the_chance.honeymart.domain.util.UnAuthorizedException

abstract class BaseRepository {

    protected suspend fun <T> wrap(function: suspend () -> BaseResponse<T>): BaseResponse<T> {
        try {
            val response = function()
            return if (response.isSuccess) {
                Log.d("Tag", "repository done correctly")
                response
            } else {
                Log.d("Tag", "repository failed")
                when (response.status.code) {
                    400 -> throw InvalidDataException()
                    401 -> throw UnAuthorizedException()
                    403 -> throw ForbiddenException()
                    404 -> throw NotFoundException()
                    409 -> throw AlreadyExistException()
                    500 -> throw InternalServerException()
                    1001 -> throw EmailIsExistException()
                    1003 -> throw EmailIsExistException()
                    1005 -> throw UnAuthorizedCredential()
                    1008 -> throw EmailIsExistException()
                    1045 -> throw MarketDeletedException()
                    else -> throw Exception(response.status.message)
                }
            }
        } catch (e: ClientRequestException) {
            Log.e("Tag", "response Error:${e.message}")
            when (e.response.status.value) {
                401 -> throw UnAuthorizedException()
                400 -> throw NotValidApiKeyException()
                500 -> throw InternalServerException()
                else -> throw Exception(e.message)
            }
        }
    }
}