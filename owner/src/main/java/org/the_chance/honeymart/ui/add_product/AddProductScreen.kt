package org.the_chance.honeymart.ui.add_product

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
                title = "Your Products is empty!!",
                subtitle = "Adding a product will increase your chances of attracting interested buyers. What product fits your item?"
            )
        }
        Column(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.dimens.space24,
                    horizontal = MaterialTheme.dimens.space16,
                )
                .weight(1f)
                .fillMaxHeight()
        ) {
            AddProductForm(
                state = state,
                onProductNameChanged = listener::onProductNameChanged,
                onProductPriceChanged = listener::onProductPriceChanged,
                onProductDescriptionChanged = listener::onProductDescriptionChanged,
                onClickAddProduct = listener::addProduct,
                omImageSelected = listener::onImagesSelected
            )
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
fun PreviewAddProductScreen() {
    HoneyMartTheme {
        AddProductScreen()
    }
}