package org.the_chance.honeymart.ui.addCategory.composable

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
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.addCategory.HoneyMartTitle
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.owner_black60
import org.the_chance.honymart.ui.theme.owner_black637
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun EmptyCategory(state: Boolean, modifier: Modifier = Modifier){
    AnimatedVisibility(
        visible = state,
        modifier = modifier
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
        ){
            HoneyMartTitle(modifier = Modifier.align(Alignment.TopStart))

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
                    text = "Your Market is empty!!",
                    style = Typography.bodyMedium.copy(color = owner_black60),
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
                    text = "Adding a category will increase your chances of attracting interested buyers. What category fits your item?",
                    style = Typography.displayLarge.copy(color = owner_black637),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .padding(top = MaterialTheme.dimens.space8)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = MaterialTheme.dimens.space10)
                            .align(Alignment.CenterVertically),
                        text = "Please click on ",
                        style = Typography.displayLarge.copy(color = owner_black637),

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
                        onClick = {}
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = MaterialTheme.dimens.space10)
                            .align(Alignment.CenterVertically),
                        text = "to Add Category",
                        style = Typography.displayLarge.copy(color = owner_black637)
                    )
                }
            }
        }
    }

}