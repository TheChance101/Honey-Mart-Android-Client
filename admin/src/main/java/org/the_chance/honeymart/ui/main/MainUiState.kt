package org.the_chance.honeymart.ui.main

data class MainUiState(
    val adminInitials: Char = ' ',
    val validationToast: ValidationToast = ValidationToast()
)

data class ValidationToast(
    val messageErrorLogout: String = "Something went wrong, please try again!"
)
