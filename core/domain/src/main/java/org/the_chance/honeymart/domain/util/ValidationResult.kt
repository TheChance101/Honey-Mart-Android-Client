package org.the_chance.honeymart.domain.util

/**
 * Created by Aziza Helmy on 6/16/2023.
 */

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMsg: String? = null
)