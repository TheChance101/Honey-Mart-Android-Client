package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.FormHeader
import org.the_chance.honeymart.ui.components.FormTextField
import org.the_chance.honeymart.ui.features.category.composable.ItemImageProductDetails
import org.the_chance.honeymart.ui.features.orders.OrdersUiState
import org.the_chance.honeymart.ui.util.toCountFormat
import org.the_chance.honeymart.ui.util.toPriceFormat
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun ProductDetailsInOrderContent(
    titleScreen: String,
    state: OrdersUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.onTertiary,
                shape = MaterialTheme.shapes.medium
            )
            .verticalScroll(rememberScrollState())
    ) {
        FormHeader(
            title = titleScreen,
            iconPainter = painterResource(id = R.drawable.icon_add_product)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            FormTextField(
                text = state.product.name,
                hint = stringResource(R.string.product_name),
                keyboardType = KeyboardType.Text,
                onValueChange = { },
                isEnable = false
            )
            FormTextField(
                text = state.product.price.toPriceFormat(),
                hint = stringResource(R.string.price),
                keyboardType = KeyboardType.Number,
                onValueChange = { },
                isEnable = false
            )
            FormTextField(
                text = state.product.count.toCountFormat(),
                hint = stringResource(R.string.description),
                keyboardType = KeyboardType.Text,
                onValueChange = { },
                isEnable = false
            )
        }
        Text(
            modifier = Modifier.padding(
                top = MaterialTheme.dimens.space24,
                start = MaterialTheme.dimens.space16
            ),
            text = stringResource(R.string.product_image),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
        )

        Row(
            modifier = Modifier
                .height(256.dp)
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.space16)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(MaterialTheme.dimens.card),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
            ) {
                items(items = state.product.images) { image ->
                    ItemImageProductDetails(image = image)
                }
            }
        }

    }
}