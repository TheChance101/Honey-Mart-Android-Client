package org.the_chance.honeymart.ui.main

data class MainUiState(
    val ownerNameFirstCharacter: String = "",
    val ownerImageUrl: String = "",
    val validationToast: ValidationToast = ValidationToast()
)
data class ValidationToast(
    val messageErrorLogout: String = "Something went wrong, please try again!"
)
