package org.the_chance.honeymart.ui.feature.product_details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.product_details.composeable.AppBar
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.composables.CustomSmallIconButton
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.composables.SpacerVertical8
import org.the_chance.honymart.ui.composables.TextPrice
import org.the_chance.honymart.ui.extension.opacity37
import org.the_chance.honymart.ui.extension.opacity60
import org.the_chance.honymart.ui.extension.opacity87
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    HoneyMartTheme {
        ProductDetailsContent(state = state,
            interaction = viewModel,
            onBackClick = {}
        )
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun ProductDetailsContent(
    state: ProductDetailsUiState,
    interaction: ProductDetailsInteraction,
    onBackClick: () -> Unit,
) {
    Scaffold(
        bottomBar = {
            CustomButton(
                labelIdStringRes = R.string.add_to_cart,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(MaterialTheme.dimens.space16)
            ) {

            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize()) {

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (imageProduct, smallImageProduct, info) = createRefs()
                Box(modifier = Modifier
                    .fillMaxHeight(0.5F)
                    .background(Color.Cyan)
                    .constrainAs(imageProduct) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                    ImageNetwork(
                        imageUrl = state.image, modifier = Modifier.fillMaxSize()
                    )

                    AppBar(
                        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16),
                        onBackClick = {}, onFavoriteClick = {},
                    )


                }

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5F)
                    .padding(top = MaterialTheme.dimens.space48)
                    .padding(
                        top = MaterialTheme.dimens.space24,
                        start = MaterialTheme.dimens.space16,
                        end = MaterialTheme.dimens.space16,

                        )
                    .constrainAs(info) {
                        top.linkTo(imageProduct.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }

                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Cyan),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.product.productName!!,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.typography.displayMedium.color.opacity87(),

                            )

                        Row {
                            CustomSmallIconButton(
                                idIconDrawableRes = R.drawable.icon_remove_from_cart
                            ) {}

                            Text(
                                text = state.quantity.toString(),
                                style = MaterialTheme.typography.displayMedium,
                                color = MaterialTheme.typography.displayMedium.color.opacity60(),
                                modifier = Modifier
                                    .padding(horizontal = MaterialTheme.dimens.space12)
                            )
                            CustomSmallIconButton(
                                idIconDrawableRes = R.drawable.icon_add_to_cart,
                                background = MaterialTheme.colorScheme.primary
                            ) {}

                        }
                    }
                    SpacerVertical8()
                    TextPrice(state.product.productPrice.toString())
                    if (state.product.productDescription != null)
                        Text(
                            state.product.productDescription,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.typography.displayMedium.color.opacity37(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.dimens.space24)
                        )


                }


                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.constrainAs(smallImageProduct) {
                        top.linkTo(imageProduct.bottom)
                        bottom.linkTo(info.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                ) {

                    items(
                        count = state.smallImages.size,

                        ) { idnex ->

                        ItemImageDetailsProduction(state.smallImages[idnex],
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        interaction.onClickSmallImage(state.smallImages[idnex])
                                    }
                                )
                                .animateContentSize()
                        )
                    }

                }

            }
        }
    }

}


@Composable
fun ItemImageDetailsProduction(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.size(88.dp)
    ) {
        ImageNetwork(
            imageUrl = imageUrl, modifier = Modifier.fillMaxSize()
        )
    }

}

//@Preview
//@Composable
//private fun ProductDetails(){
//    HoneyMartTheme {
//        ProductDetailsContent()
//    }
//}

