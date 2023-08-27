package org.the_chance.honeymart.ui.features.coupons.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.category.composable.ProductCard
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductSearchContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        HoneyTextField(
            modifier = Modifier
                .fillMaxWidth(),
            text = "",
            hint = "Search",
            iconPainter = painterResource(id = R.drawable.search),
            onValueChange = {},
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
        ) {
            repeat(10) {
                item {
                    ProductCard(
                        onClick = { /*TODO*/ },
                        productName = "Product Name",
                        productPrice = "30,000\$",
                        imageUrl = "http://192.168.1.3:8080/files/market/1/product0.3437805634524187154409634312406392.png"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductSearchContentPreview() {
    HoneyMartTheme {
        ProductSearchContent()
    }
}