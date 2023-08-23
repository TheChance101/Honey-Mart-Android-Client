package org.the_chance.honeymart.ui.feature.profile

import org.the_chance.honeymart.domain.model.ProfileUserEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isDark: Boolean = false,
    val error: ErrorHandler? = null,
    val isConnectionError: Boolean = false,
    val isShowDialog: Boolean = false,
    val accountInfo: AccountState = AccountState(),
    val image: ByteArray? = null,
) {
    data class AccountState(
        val userId: Long = 0,
        val fullName: String = "",
        val email: String = "",
        val profileImage: String = "",
    )
}

fun ProfileUserEntity.toProfileUiState(): ProfileUiState.AccountState {
    return ProfileUiState.AccountState(
        userId = userId,
        fullName = fullName,
        email = email,
        profileImage = profileImage,
    )
}