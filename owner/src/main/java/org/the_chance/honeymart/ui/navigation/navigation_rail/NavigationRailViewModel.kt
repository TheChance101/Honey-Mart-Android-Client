package org.the_chance.honeymart.ui.navigation.navigation_rail

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetOwnerInfoUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationRailViewModel @Inject constructor(
    private val profileInfo: GetOwnerInfoUseCase,
) : BaseViewModel<NavigationRailUiState, NavigationRailEffect>(NavigationRailUiState()),
    NavigationRailInteractionListener {

    override val TAG: String
        get() = this::class.simpleName.toString()

    init {
        getOwnerInfo()
    }

    private fun getOwnerInfo() {
        _state.update {
            it.copy(
                ownerNameFirstCharacter = profileInfo.getOwnerNameFirstCharacter(),
                ownerImageUrl = profileInfo.getOwnerImageUrl()
            )
        }
    }

    override fun onClickProfile() {
        effectActionExecutor(_effect, NavigationRailEffect.OnClickProfileEffect)
    }

    override fun onClickLogout() {
        effectActionExecutor(_effect, NavigationRailEffect.OnClickLogoutEffect)
    }
}