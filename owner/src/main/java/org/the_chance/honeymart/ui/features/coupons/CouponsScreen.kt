package org.the_chance.honeymart.ui.features.coupons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.coupons.content.AddCouponContent
import org.the_chance.honeymart.ui.features.coupons.content.ProductSearchContent
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CouponsScreen(
    viewModel: CouponsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    CouponsContent(state = state, listener = viewModel)
}

@Composable
fun CouponsContent(
    state: CouponsUiState,
    listener: CouponsInteractionListener,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            HoneyMartTitle()
            ProductSearchContent()
        }
        AddCouponContent(
            modifier = Modifier
                .weight(1f)
                .padding(top = MaterialTheme.dimens.space56),
            state = state.addCoupon,
        )
    }
}

@Preview(device = "id:pixel_tablet")
@Composable
fun CouponsScreenPreview() {
    HoneyMartTheme {
        CouponsScreen()
    }
}