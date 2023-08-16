package org.the_chance.honeymart.ui.feature.profile

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsInteraction
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProfileUiState, ProfileUiEffect>(ProfileUiState()),
    ProfileInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()




    override fun onClickMyOrder() {
        TODO("Not yet implemented")
    }

    override fun onClickCoupons() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }

    override fun onClickTheme() {
        TODO("Not yet implemented")
    }

    override fun onClickLogout() {
        TODO("Not yet implemented")
    }

    override fun showSnackBar(massage: String) {
        TODO("Not yet implemented")
    }

    override fun showDialog(massage: String) {
        TODO("Not yet implemented")
    }

    override fun updateImage() {
        TODO("Not yet implemented")
    }
}