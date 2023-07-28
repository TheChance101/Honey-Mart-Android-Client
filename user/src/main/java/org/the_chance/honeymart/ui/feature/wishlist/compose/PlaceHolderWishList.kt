package org.the_chance.honeymart.ui.feature.wishlist.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black55
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun PlaceHolderWishList(
    onClickDiscoverButton: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.space16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.placeholder_wish_list),
            contentDescription = " "
        )
        Text(
            text = "Your Wish List is empty!! ",
            modifier = Modifier.padding(top = MaterialTheme.dimens.space32),
            style = Typography.bodyMedium,
            color = black55,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Unlock the power of favorites and indulge in a world of personalized shopping.",
            style = Typography.displayLarge,
            color = black37,
            textAlign = TextAlign.Center
        )
        CustomButton(
            labelIdStringRes = org.the_chance.user.R.string.discover_market_now,
            idIconDrawableRes = R.drawable.icon_cart,
            modifier = Modifier.padding(top = MaterialTheme.dimens.space56),
            onClick = onClickDiscoverButton
        )
    }
}