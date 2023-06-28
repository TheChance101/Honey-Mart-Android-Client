package org.the_chance.honeymart.data.repository

import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.domain.util.AlreadyExistException
import org.the_chance.honeymart.domain.util.EmailIsExistException
import org.the_chance.honeymart.domain.util.ForbiddenException
import org.the_chance.honeymart.domain.util.InternalServerException
import org.the_chance.honeymart.domain.util.InvalidDataException
import org.the_chance.honeymart.domain.util.NotFoundException
import org.the_chance.honeymart.domain.util.UnAuthorizedException
import retrofit2.Response

abstract class BaseRepository {


    protected suspend fun <T> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
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