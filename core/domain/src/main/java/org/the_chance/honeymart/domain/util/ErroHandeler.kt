package org.the_chance.honeymart.domain.util

open class GeneralException : Exception()
class InvalidDataException : GeneralException()
class NotFoundException : GeneralException()
class AlreadyExistException : GeneralException()


open class NetworkException : Exception()
class NoConnectionException : NetworkException()
class InternalServerException : NetworkException()


open class AuthenticationException : Exception()
class UnAuthorizedException : AuthenticationException()
class NotValidApiKeyException : AuthenticationException()
class EmailIsExistException : AuthenticationException()
class ForbiddenException : AuthenticationException()
class UnAuthorizedCredential : AuthenticationException()
class MarketDeletedException : AuthenticationException()
class InvalidEmailOrPassword : AuthenticationException()

sealed interface ErrorHandler {
    object NoConnection : ErrorHandler
    object ServerError : ErrorHandler

    object InvalidData : ErrorHandler
    object NotFound : ErrorHandler
    object UnAuthorizedUser : ErrorHandler
    object AlreadyExist : ErrorHandler
    object UnKnownError : ErrorHandler
    object MarketDeleted : ErrorHandler

}


fun handelNetworkException(
    exception: NetworkException, onError: (ErrorHandler) -> Unit,
) {
    when (exception) {
        is InternalServerException -> onError(ErrorHandler.ServerError)

        is NoConnectionException -> onError(ErrorHandler.NoConnection)
    }
}

fun handelAuthenticationException(
    exception: AuthenticationException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is EmailIsExistException -> onError(ErrorHandler.AlreadyExist)

        is ForbiddenException -> onError(ErrorHandler.UnAuthorizedUser)

        is UnAuthorizedException -> onError(ErrorHandler.UnAuthorizedUser)

        is UnAuthorizedCredential -> onError(ErrorHandler.UnAuthorizedUser)

        is NotValidApiKeyException -> onError(ErrorHandler.UnKnownError)

        is MarketDeletedException -> onError(ErrorHandler.MarketDeleted)
    }
}

fun handelGeneralException(
    exception: GeneralException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidDataException -> onError(ErrorHandler.InvalidData)

        is NotFoundException -> onError(ErrorHandler.NotFound)

        is AlreadyExistException -> onError(ErrorHandler.AlreadyExist)
    }
}
