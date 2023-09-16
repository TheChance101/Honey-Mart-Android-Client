package org.the_chance.honeymart.ui.features.profile

import arrow.optics.optics
import org.the_chance.honeymart.domain.model.MarketInfo
import org.the_chance.honeymart.domain.model.OwnerProfile
import org.the_chance.honeymart.domain.util.ErrorHandler

// region Ui State
@optics
data class ProfileUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val personalInfo: PersonalInfoUiState = PersonalInfoUiState(),
    val marketInfo: MarketInfoUiState = MarketInfoUiState(),
    val showMarketStatusDialog: Boolean = false,
) {
    companion object
}

@optics
data class PersonalInfoUiState(
    val id: Long = 0L,
    val icon: Char = 'H',
    val name: String = "",
    val email: String = ""
) {
    companion object
}

@optics
data class MarketInfoUiState(
    val id: Long = 0L,
    val status: MarketStatus = MarketStatus.OFFLINE,
    val image: String = "",
    val name: String = "",
    val address: String = "",
    val description: String = ""
) {
    companion object
}

enum class MarketStatus(val state: Int) {
    ONLINE(1),
    OFFLINE(2)
}
// endregion

// region Mapper
fun OwnerProfile.toPersonalInfoUiState(): PersonalInfoUiState {
    return PersonalInfoUiState(
        id = ownerId,
        icon = fullName.first(),
        name = fullName,
        email = email
    )
}

fun MarketInfo.toMarketInfoUiState(): MarketInfoUiState {
    val status = when (marketStatus) {
        true -> MarketStatus.ONLINE
        false -> MarketStatus.OFFLINE
    }

    return MarketInfoUiState(
        id = marketId,
        status = status,
        image = imageUrl,
        name = marketName,
        address = address,
        description = description
    )
}
// endregion

// region Extensions
fun ProfileUiState.showContent() = !isError && error == null && !isLoading
// endregion