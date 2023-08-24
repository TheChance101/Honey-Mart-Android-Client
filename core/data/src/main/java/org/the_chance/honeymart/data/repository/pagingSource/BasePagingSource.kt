package org.the_chance.honeymart.data.repository.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.ktor.client.plugins.ClientRequestException
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.util.AlreadyExistException
import org.the_chance.honeymart.domain.util.EmailIsExistException
import org.the_chance.honeymart.domain.util.ForbiddenException
import org.the_chance.honeymart.domain.util.InternalServerException
import org.the_chance.honeymart.domain.util.InvalidDataException
import org.the_chance.honeymart.domain.util.NotFoundException
import org.the_chance.honeymart.domain.util.NotValidApiKeyException
import org.the_chance.honeymart.domain.util.UnAuthorizedException

abstract class BasePagingSource<Value : Any> (
    protected val honeyMartService: HoneyMartService
) : PagingSource<Int, Value>() {

    protected abstract suspend fun fetchData(page: Int): List<Value>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        val currentPage = params.key ?: 1
        return try {
            val response = fetchData(currentPage)
            val nextKey = (currentPage + 1).takeIf { response.lastIndex >= currentPage }
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    protected suspend fun <T> wrap(function: suspend () -> BaseResponse<T>): BaseResponse<T> {
        try {
            val response = function()
            return if (response.isSuccess) {
                Log.e("Tag", "response success:${response.value}")
                response
            } else {
                Log.e("Tag", "response fail:${response.status.code}")
                when (response.status.code) {
                    400 -> throw InvalidDataException()
                    401 -> throw UnAuthorizedException()
                    403 -> throw ForbiddenException()
                    404 -> throw NotFoundException()
                    409 -> throw AlreadyExistException()
                    500 -> throw InternalServerException()
                    1001 -> throw EmailIsExistException()
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