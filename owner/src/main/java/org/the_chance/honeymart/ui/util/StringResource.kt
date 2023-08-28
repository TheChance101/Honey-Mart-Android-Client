package org.the_chance.honeymart.ui.util

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

interface StringResource {
    val validationStringDictionary : Map<ValidationState,String>
    val errorStringDictionary: Map<ErrorHandler,String>
    val requiredFieldsMessageString: String
}