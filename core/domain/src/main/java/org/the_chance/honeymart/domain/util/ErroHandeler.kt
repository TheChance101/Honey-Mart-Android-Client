package org.the_chance.honeymart.domain.util

/**
 * Created by Asia sama on 6/17/2023.
 * sehunexo710@gmail.com
 */

class InvalidEmailException(message: String = "This Email already exist") : Exception(message)
class ProductNotInSameCartMarketException(message: String = "this product not in same market, you should delete cart") :
    Exception(message)

class UnAuthorizedException(message: String = "UnAuthorized User") : Exception(message)
class InvalidPasswordException(message: String = "Password should be at least has one letter, one special character, one number, and a total length between 8 to 14 characters") :
    Exception(message)

class InvalidFullNameException(message: String = "Name is not valid") : Exception(message)
class InvalidRegisterException(message: String = " is not valid") : Exception(message)
class NoNetworkException(message: String = "No connection") : Exception(message)
class InvalidEmailOrPasswordException(message: String = "Name is not valid") : Exception(message)


sealed interface ErrorHandler {
    object EmailIsExist : ErrorHandler
    object InvalidRegister : ErrorHandler
    object NoNetwork : ErrorHandler
    object InvalidEmailAndPassword : ErrorHandler
    object UnAuthorized : ErrorHandler
    object NoError : ErrorHandler

}