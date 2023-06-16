package org.the_chance.honeymart.domain.usecase

import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class ValidateEmailUseCase @Inject constructor() {

   operator fun invoke(email: String): Boolean {
       if (email.isBlank()) {
           return false
       }
       if (!isEmail(email)) {
           return false
       }
       return true
   }

     private fun isEmail(email: String): Boolean {
        return Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
            .matcher(email).matches()
    }
}