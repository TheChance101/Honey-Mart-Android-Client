package org.the_chance.honeymart.data.repository

import android.util.Log
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.domain.util.*


abstract class BaseRepository {

    protected suspend fun <T> wrap(function: suspend () -> BaseResponse<T>): BaseResponse<T> {
        try {
            val response = function()
            return if (response.isSuccess) {
                Log.d("Tag", "repository done correctly")
                response
            } else {
                Log.d("Tag", "repository failed")
                Log.d("Tarek", response.status.code.toString())
                when (response.status.code) {
                    1002 -> throw InvalidInputException()
                    1003 -> throw UsernameAlreadyExistException()
                    1004 -> throw InvalidUserIdException()
                    1005 -> throw InvalidUserNameOrPasswordException()
                    1006 -> throw InvalidEmailException()
                    1007 -> throw InvalidNameException()
                    1008 -> throw EmailAlreadyExistException()
                    1009 -> throw InvalidUserNameInputException()
                    1010 -> throw InvalidPasswordInputException()
                    1011 -> throw UnKnownUserException()
                    1022 -> throw InvalidOwnerIdException()
                    1032 -> throw AdminAccessDeniedException()
                    1042 -> throw InvalidMarketIdException()
                    1043 -> throw InvalidMarketNameException()
                    1044 -> throw InvalidMarketDescriptionException()
                    1045 -> throw MarketDeletedException()
                    1046 -> throw MarketAlreadyExistException()
                    1047 -> throw MarketNotApprovedException()
                    1052 -> throw InvalidCategoryIdException()
                    1053 -> throw InvalidCategoryNameException()
                    1054 -> throw CategoryDeletedException()
                    1055 -> throw CategoryNameNotUniqueException()
                    1056 -> throw NotValidCategoryListException()
                    1062 -> throw InvalidProductIdException()
                    1063 -> throw InvalidProductNameException()
                    1064 -> throw InvalidProductDescriptionException()
                    1065 -> throw InvalidProductPriceException()
                    1066 -> throw ProductDeletedException()
                    1067 -> throw ProductAlreadyInWishListException()
                    1068 -> throw ProductNotInSameCartMarketException()
                    1072 -> throw InvalidOrderIdException()
                    1073 -> throw InvalidStateOrderException()
                    1074 -> throw CantUpdateOrderStateException()
                    1082 -> throw EmptyCartException()
                    1083 -> throw CountInvalidInputException()
                    1084 -> throw InvalidPercentageException()
                    1092 -> throw InvalidImageIdException()
                    1093 -> throw ImageNotFoundException()
                    1094 -> throw AddImageFailedException()
                    1102 -> throw InvalidCouponIdException()
                    1103 -> throw InvalidCouponException()
                    1104 -> throw CouponAlreadyClippedException()
                    1105 -> throw InvalidExpirationDateException()
                    1106 -> throw InvalidCountException()
                    1112 -> throw UnauthorizedException()
                    1113 -> throw InvalidApiKeyException()
                    1114 -> throw InvalidTokenException()
                    1115 -> throw InvalidRuleException()
                    1116 -> throw TokenExpiredException()
                    1117 -> throw InvalidTokenTypeException()
                    1118 -> throw InvalidDeviceTokenException()
                    1119 -> throw IdNotFoundException()
                    1120 -> throw InvalidPageNumberException()
                    1121 -> throw MissingQueryParameterException()
                    else -> throw Exception(response.status.message)
                }
            }
        } catch (e: ClientRequestException) {
            Log.e("Tag", "response Error:${e.message}")
            when (e.response.status.value) {
                HttpStatusCode.BadGateway.value -> throw NoConnectionException()
                HttpStatusCode.BadRequest.value -> throw InvalidDataException()
                HttpStatusCode.Unauthorized.value -> throw UnAuthorizedException()
                HttpStatusCode.Forbidden.value ->throw ForbiddenException()
                HttpStatusCode.NotFound.value ->throw NotFoundException()
                HttpStatusCode.Conflict.value -> throw AlreadyExistException()
                HttpStatusCode.InternalServerError.value ->throw  InternalServerException()
                else -> throw Exception(e.message)
            }
        }
    }
}