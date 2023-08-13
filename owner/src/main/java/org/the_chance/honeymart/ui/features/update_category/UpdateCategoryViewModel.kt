package org.the_chance.honeymart.ui.features.update_category

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.domain.usecase.UpdateCategoryUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateCategoryViewModel @Inject constructor(
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : BaseViewModel<UpdateCategoryUiState, Unit>(UpdateCategoryUiState()),
    UpdateCategoryInteractionListener {

    override val TAG: String = this::class.java.simpleName

}