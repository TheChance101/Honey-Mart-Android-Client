package org.the_chance.honeymart.ui.add_product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductForm() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space24
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_cart),
                contentDescription = "Icon Cart",
                modifier = Modifier
                    .padding(end = MaterialTheme.dimens.space8)
                    .size(MaterialTheme.dimens.icon24),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Add New Product",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }
        for (i in listOf("Product name", "Number of items", "Price", "Description")) {
            FormTextField(
                modifier = Modifier.padding(bottom = MaterialTheme.dimens.space8),
                hint = i,
                onValueChange = { }
            )
        }
        Text(
            modifier = Modifier.padding(
                top = MaterialTheme.dimens.space24,
                start = MaterialTheme.dimens.space16
            ),
            text = "Add product image",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
        )
        Card(
            modifier = Modifier
                .padding(
                    top = MaterialTheme.dimens.space16,
                    start = MaterialTheme.dimens.space16
                )
                .size(102.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
            onClick = { }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .size(MaterialTheme.dimens.icon48),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_add_product),
                    contentDescription = "Icon Cart",
                    modifier = Modifier.size(MaterialTheme.dimens.icon24),
                    tint = black60
                )
            }
        }
        Spacer(modifier = Modifier.weight(1F))
        HoneyFilledIconButton(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space24
            ),
            label = "Add",
            iconPainter = painterResource(R.drawable.icon_add_product),
            onClick = { }
        )
    }
}

@Preview(name = "Add Product Screen", device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewAddProductForm() {
    HoneyMartTheme {
        AddProductForm()
    }
}