package org.the_chance.honeymart.domain.util

/**
 * Created by Asia sama on 6/17/2023.
 * sehunexo710@gmail.com
 */

class InvalidEmailException(message: String = "Email is not valid") : Exception(message)
class InvalidPasswordException(message: String = "Password should be at least has one letter, one special character, one number, and a total length between 8 to 14 characters") :
    Exception(message)

class InvalidFullNameException(message: String = "Name is not valid") : Exception(message)
