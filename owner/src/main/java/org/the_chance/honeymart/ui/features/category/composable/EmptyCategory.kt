package org.the_chance.honeymart.ui.features.category.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.blackOn37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun EmptyCategory(state: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    AnimatedVisibility(
        visible = state,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Image(
                    painter = painterResource(id = R.drawable.img_empty_category),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    modifier = Modifier
                        .padding(top = MaterialTheme.dimens.space32)
                        .fillMaxWidth(),
                    text = stringResource(R.string.your_market_is_empty),
                    style = Typography.bodyMedium.copy(color = black60),
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.dimens.space16,
                            start = MaterialTheme.dimens.space86,
                            end = MaterialTheme.dimens.space86
                        )
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.body_empty_category),
                    style = Typography.displayLarge.copy(color = blackOn37),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .padding(top = MaterialTheme.dimens.space8)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = MaterialTheme.dimens.space8)
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.please_click_on),
                        style = Typography.displayLarge.copy(color = blackOn37),

                        )

                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(primary100),
                        content = {
                            Icon(
                                modifier = Modifier.size(MaterialTheme.dimens.icon14),
                                painter = painterResource(id = R.drawable.icon_add_product),
                                contentDescription = "",
                                tint = Color.White
                            )
                        },
                        onClick = onClick
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = MaterialTheme.dimens.space8)
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.to_add_category),
                        style = Typography.displayLarge.copy(color = blackOn37)
                    )
                }
            }
        }
    }

}