package org.the_chance.honeymart.ui.feature.cart.Composeables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.cart.CartViewModel
import org.the_chance.honeymart.ui.feature.uistate.CartUiState
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.user.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPlaceholder(
    sheetState: SheetState,
    state: CartUiState,
    viewModel: CartViewModel = hiltViewModel(),
    onClickButtonDiscover : () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = org.the_chance.design_system.R.drawable.placeholder_order),
            contentDescription = null,
        )

        Text(
            text = "Your Cart list is empty!!",
            color = black60,
            style = org.the_chance.honymart.ui.theme.Typography.bodyMedium,
            modifier = Modifier.padding(top = 32.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Text(
            text = "It's a carttastrophe! Let's fix it by adding items that catch your eye!",
            color = black37,
            style = org.the_chance.honymart.ui.theme.Typography.displayLarge,

            modifier = Modifier.padding(top = 16.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        CustomButton(
            labelIdStringRes = R.string.discover_markets_now,
            idIconDrawableRes = org.the_chance.design_system.R.drawable.icon_cart,
            modifier = Modifier.padding(top = 16.dp),
            background = primary100,
            onClick = onClickButtonDiscover
        )

        LaunchedEffect(key1 = state.products){
            if (state.bottomSheetIsDisplayed) sheetState.expand() else sheetState.hide()
            viewModel.changeBottomSheetValue()

        }

    }
}
