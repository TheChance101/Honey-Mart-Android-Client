package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState

interface IValidationUseCase {
    fun validateEmail(email: String): ValidationState
    fun validateConfirmPassword(password: String, repeatedPassword: String): Boolean
    fun validationFullName(fullName: String): ValidationState
    fun validationPassword(password: String): ValidationState
}