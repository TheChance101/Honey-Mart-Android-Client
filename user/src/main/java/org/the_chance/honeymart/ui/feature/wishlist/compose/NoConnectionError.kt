
package org.the_chance.honeymart.ui.feature.wishlist.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black55
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun NoConnectionError(
    getWishListProducts: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.space16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.no_connection_placeholder
            ),
            contentDescription = " ",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Text(
            text = "Oops, No connection!!!",
            style = Typography.bodyMedium,
            color = black55,
            textAlign = TextAlign.Center,
        )
        CustomButton(
            labelIdStringRes = R.string.try_again,
            onClick = getWishListProducts,
            modifier = Modifier.padding(top = MaterialTheme.dimens.space16).wrapContentWidth()

        )
    }
}
@Preview
@Composable
fun PreviewNoConnectionError(){
    NoConnectionError({})
}