package org.the_chance.honeymart.ui.features.signup.market_info

import org.the_chance.honeymart.domain.util.ErrorHandler

/**
 * Created by Aziza Helmy on 8/9/2023.
 */
data class MarketInfoUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isSignUp: Boolean = false,
    val marketName: String = "",
    val address: String = "",
    val description: String = "",
    val marketImage: String = "",
    val showToast: Boolean = false
)