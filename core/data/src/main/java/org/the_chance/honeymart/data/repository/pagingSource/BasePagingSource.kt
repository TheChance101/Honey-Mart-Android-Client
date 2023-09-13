package org.the_chance.honeymart.data.repository.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.util.AddImageFailedException
import org.the_chance.honeymart.domain.util.AdminAccessDeniedException
import org.the_chance.honeymart.domain.util.CantUpdateOrderStateException
import org.the_chance.honeymart.domain.util.CategoryDeletedException
import org.the_chance.honeymart.domain.util.CategoryNameNotUniqueException
import org.the_chance.honeymart.domain.util.CountInvalidInputException
import org.the_chance.honeymart.domain.util.CouponAlreadyClippedException
import org.the_chance.honeymart.domain.util.EmailAlreadyExistException
import org.the_chance.honeymart.domain.util.EmptyCartException
import org.the_chance.honeymart.domain.util.ForbiddenException
import org.the_chance.honeymart.domain.util.IdNotFoundException
import org.the_chance.honeymart.domain.util.ImageNotFoundException
import org.the_chance.honeymart.domain.util.InternalServerException
import org.the_chance.honeymart.domain.util.InvalidApiKeyException
import org.the_chance.honeymart.domain.util.InvalidCategoryIdException
import org.the_chance.honeymart.domain.util.InvalidCategoryNameException
import org.the_chance.honeymart.domain.util.InvalidCountException
import org.the_chance.honeymart.domain.util.InvalidCouponException
import org.the_chance.honeymart.domain.util.InvalidCouponIdException
import org.the_chance.honeymart.domain.util.InvalidDataException
import org.the_chance.honeymart.domain.util.InvalidDeviceTokenException
import org.the_chance.honeymart.domain.util.InvalidEmailException
import org.the_chance.honeymart.domain.util.InvalidExpirationDateException
import org.the_chance.honeymart.domain.util.InvalidImageIdException
import org.the_chance.honeymart.domain.util.InvalidInputException
import org.the_chance.honeymart.domain.util.InvalidMarketDescriptionException
import org.the_chance.honeymart.domain.util.InvalidMarketIdException
import org.the_chance.honeymart.domain.util.InvalidMarketNameException
import org.the_chance.honeymart.domain.util.InvalidNameException
import org.the_chance.honeymart.domain.util.InvalidOrderIdException
import org.the_chance.honeymart.domain.util.InvalidOwnerIdException
import org.the_chance.honeymart.domain.util.InvalidPageNumberException
import org.the_chance.honeymart.domain.util.InvalidPasswordInputException
import org.the_chance.honeymart.domain.util.InvalidPercentageException
import org.the_chance.honeymart.domain.util.InvalidProductDescriptionException
import org.the_chance.honeymart.domain.util.InvalidProductIdException
import org.the_chance.honeymart.domain.util.InvalidProductNameException
import org.the_chance.honeymart.domain.util.InvalidProductPriceException
import org.the_chance.honeymart.domain.util.InvalidRuleException
import org.the_chance.honeymart.domain.util.InvalidStateOrderException
import org.the_chance.honeymart.domain.util.InvalidTokenException
import org.the_chance.honeymart.domain.util.InvalidTokenTypeException
import org.the_chance.honeymart.domain.util.InvalidUserIdException
import org.the_chance.honeymart.domain.util.InvalidUserNameInputException
import org.the_chance.honeymart.domain.util.InvalidUserNameOrPasswordException
import org.the_chance.honeymart.domain.util.MarketAlreadyExistException
import org.the_chance.honeymart.domain.util.MarketDeletedException
import org.the_chance.honeymart.domain.util.MarketNotApprovedException
import org.the_chance.honeymart.domain.util.MissingQueryParameterException
import org.the_chance.honeymart.domain.util.NoConnectionException
import org.the_chance.honeymart.domain.util.NotFoundException
import org.the_chance.honeymart.domain.util.NotValidCategoryListException
import org.the_chance.honeymart.domain.util.ProductAlreadyInWishListException
import org.the_chance.honeymart.domain.util.ProductDeletedException
import org.the_chance.honeymart.domain.util.ProductNotInSameCartMarketException
import org.the_chance.honeymart.domain.util.TokenExpiredException
import org.the_chance.honeymart.domain.util.UnAuthorizedException
import org.the_chance.honeymart.domain.util.UnKnownUserException
import org.the_chance.honeymart.domain.util.UsernameAlreadyExistException

abstract class BasePagingSource<Value : Any>(
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
                Log.d("Tag", "repository failed :$response.status.message")
                throw getExceptionForStatusCode(response.status.code)
            }
        } catch (e: ClientRequestException) {
            Log.e("Tag", "response Error:${e.message}")
            throw networkExceptionStatusCode(e.response.status.value)
        }
    }

    private fun getExceptionForStatusCode(statusCode: Int): Exception {
        return exceptionMap[statusCode] ?: Exception("Unhandled status code: $statusCode")
    }

    private fun networkExceptionStatusCode(httpStatusCode: Int): Exception {
        return when (httpStatusCode) {
            HttpStatusCode.BadGateway.value -> NoConnectionException()
            HttpStatusCode.BadRequest.value -> InvalidDataException()
            HttpStatusCode.Unauthorized.value -> UnAuthorizedException()
            HttpStatusCode.Forbidden.value -> ForbiddenException()
            HttpStatusCode.NotFound.value -> NotFoundException()
            HttpStatusCode.InternalServerError.value -> InternalServerException()
            else -> Exception("Unhandled HTTP status code: $httpStatusCode")
        }
    }

    companion object {
        private val exceptionMap = mapOf(
            1002 to InvalidInputException(),
            1003 to UsernameAlreadyExistException(),
            1004 to InvalidUserIdException(),
            1005 to InvalidUserNameOrPasswordException(),
            1006 to InvalidEmailException(),
            1007 to InvalidNameException(),
            1008 to EmailAlreadyExistException(),
            1009 to InvalidUserNameInputException(),
            1010 to InvalidPasswordInputException(),
            1011 to UnKnownUserException(),
            1022 to InvalidOwnerIdException(),
            1032 to AdminAccessDeniedException(),
            1042 to InvalidMarketIdException(),
            1043 to InvalidMarketNameException(),
            1044 to InvalidMarketDescriptionException(),
            1045 to MarketDeletedException(),
            1046 to MarketAlreadyExistException(),
            1047 to MarketNotApprovedException(),
            1052 to InvalidCategoryIdException(),
            1053 to InvalidCategoryNameException(),
            1054 to CategoryDeletedException(),
            1055 to CategoryNameNotUniqueException(),
            1056 to NotValidCategoryListException(),
            1062 to InvalidProductIdException(),
            1063 to InvalidProductNameException(),
            1064 to InvalidProductDescriptionException(),
            1065 to InvalidProductPriceException(),
            1066 to ProductDeletedException(),
            1067 to ProductAlreadyInWishListException(),
            1068 to ProductNotInSameCartMarketException(),
            1072 to InvalidOrderIdException(),
            1073 to InvalidStateOrderException(),
            1074 to CantUpdateOrderStateException(),
            1082 to EmptyCartException(),
            1083 to CountInvalidInputException(),
            1084 to InvalidPercentageException(),
            1092 to InvalidImageIdException(),
            1093 to ImageNotFoundException(),
            1094 to AddImageFailedException(),
            1102 to InvalidCouponIdException(),
            1103 to InvalidCouponException(),
            1104 to CouponAlreadyClippedException(),
            1105 to InvalidExpirationDateException(),
            1106 to InvalidCountException(),
            1113 to InvalidApiKeyException(),
            1114 to InvalidTokenException(),
            1115 to InvalidRuleException(),
            1116 to TokenExpiredException(),
            1117 to InvalidTokenTypeException(),
            1118 to InvalidDeviceTokenException(),
            1119 to IdNotFoundException(),
            1120 to InvalidPageNumberException(),
            1121 to MissingQueryParameterException()
        )
    }
}