package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class ValidateEmailUseCase @Inject constructor() {

   operator fun invoke(email: String): ValidationState {
       if (email.isBlank()) {
           return ValidationState.BLANK_EMAIL
       }
       if (!isEmail(email)) {
           return ValidationState.INVALID_EMAIL
       }
       return ValidationState.VALID_EMAIL
   }

     private fun isEmail(email: String): Boolean {
        return Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
            .matcher(email).matches()
    }
}