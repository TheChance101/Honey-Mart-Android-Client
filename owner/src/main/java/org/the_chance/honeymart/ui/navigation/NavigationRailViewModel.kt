package org.the_chance.honeymart.ui.navigation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetUserInfoUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationRailViewModel @Inject constructor(
    private val profileInfo: GetUserInfoUseCase,
)
    : BaseViewModel<NavigationRailUiState , NavigationRailEffect>(NavigationRailUiState()){
    override val TAG: String
        get() = this::class.simpleName.toString()


    private fun getUserInfo() {
        _state.update { it.copy(
        ) }
    }
}