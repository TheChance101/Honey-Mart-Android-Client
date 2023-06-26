package org.the_chance.honeymart.domain.util

open class GeneralException : Exception()
class UnAuthorizedException : GeneralException()//401
class InvalidDataException : GeneralException()//400
class NotFoundException : GeneralException()//404
class AlreadyExistException : GeneralException()//409


open class NetworkException : Exception()
class NoConnectionException : NetworkException()
class InternalServerException : NetworkException()//500


open class AuthenticationException : Exception()
class EmailIsExistException : AuthenticationException()//1001
class ForbiddenException : AuthenticationException()//403


sealed interface ErrorHandler {
    object EmailIsExist : ErrorHandler

    object NoConnection : ErrorHandler
    object ServerError : ErrorHandler

    object InvalidData : ErrorHandler
    object NotFound : ErrorHandler
    object UnAuthorizedUser : ErrorHandler
    object AlreadyExist : ErrorHandler
    object UnKnownError : ErrorHandler

}


//////httm code
//Created 201 post
// ok 200 delete update get
// 404 notFound
// 302 found get owner profile

//class ProductNotInSameCartMarketException(message: String = "this product not in same market, you should delete cart") :
//    Exception(message)
//
//
//open class RegisterException() : Exception()
//class InvalidFullNameException : RegisterException()
//class InvalidEmailException : RegisterException()
//
//class InvalidEmailOrPasswordException : RegisterException()
//class InvalidRegisterException : RegisterException()
//
//class InvalidPasswordException : RegisterException()