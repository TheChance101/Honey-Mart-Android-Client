package org.the_chance.honeymart.ui.util

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

interface StringDictionary {
    val validationString : Map<ValidationState,String>
    val errorString: Map<ErrorHandler,String>
    val requiredFieldsMessageString: String
}