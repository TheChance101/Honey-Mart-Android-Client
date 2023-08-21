package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun LastPurchasesItems(
    image: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(
                width = MaterialTheme.dimens.widthItemLastPurchases,
                height = MaterialTheme.dimens.heightItemMarketCard
            )
            .clip(RoundedCornerShape(MaterialTheme.dimens.space16))
            .clickable { onClick() }
    ) {
        Column {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                ImageNetwork(
                    imageUrl = image,
                    contentDescription = stringResource(id = R.string.market_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(topStart = MaterialTheme.dimens.space16))
                        .background(color = Color.Red)
                )
                ImageNetwork(
                    imageUrl = image,
                    contentDescription = stringResource(id = R.string.market_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(topEnd = MaterialTheme.dimens.space16))
                        .background(color = Color.Green)
                )
            }
            Row(
                modifier = Modifier.weight(1f)
            ) {
                ImageNetwork(
                    imageUrl = image,
                    contentDescription = stringResource(id = R.string.market_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(bottomStart = MaterialTheme.dimens.space16))
                        .background(color = Color.Blue)
                )
                ImageNetwork(
                    imageUrl = image,
                    contentDescription = stringResource(id = R.string.market_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(bottomEnd = MaterialTheme.dimens.space16))
                        .background(color = Color.Yellow)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.space8))

            Text(
                text = label,
                style = Typography.displayLarge.copy(black60),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}

@Preview
@Composable
fun LastPurchasesItemsPreview() {
    HoneyMartTheme {
        LastPurchasesItems(
            image = "",
            label = "Last Purchases",
            onClick = {}
        )
    }
}



