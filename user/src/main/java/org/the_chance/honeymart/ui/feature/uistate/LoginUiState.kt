package org.the_chance.honeymart.ui.feature.uistate

/**
 * Created by Aziza Helmy on 6/16/2023.
 */

data class LoginUiState(
    val isLoading: Boolean = true,
    val error: List<String> = emptyList(),
    val email: String,
    val password:String

)