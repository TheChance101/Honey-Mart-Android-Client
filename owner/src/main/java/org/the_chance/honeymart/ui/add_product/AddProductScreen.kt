package org.the_chance.honeymart.ui.add_product

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.add_product.components.AddProductForm
import org.the_chance.honeymart.ui.components.PlaceHolderItem
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    AddProductContent(state = state, listener = viewModel)
}

@Composable
fun AddProductContent(
    state: AddProductUiState,
    listener: AddProductInteractionListener
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            PlaceHolderItem(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space56),
                visibility = true,
                title = stringResource(R.string.your_products_is_empty),
                subtitle = stringResource(R.string.add_product_placeholder_subtitle)
            )
        }
        Column(
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
        ) {
            AddProductForm(state = state, listener = listener,)
        }
    }
}

@Preview(
    name = "Add Product Screen",
    device = Devices.TABLET,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun PreviewAddProductScreen() {
    HoneyMartTheme {
        AddProductScreen()
    }
}