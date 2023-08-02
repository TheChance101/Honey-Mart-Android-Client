package org.the_chance.honeymart.ui.feature.product_details


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.authentication.navigateToAuth
import org.the_chance.honeymart.ui.feature.product_details.composeable.AppBar
import org.the_chance.honeymart.ui.feature.product_details.composeable.SmallProductImages
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.composables.CustomSmallIconButton
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.SpacerVertical8
import org.the_chance.honymart.ui.composables.TextPrice
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current

    HoneyMartTheme {
        ProductDetailsContent(state = state,
            interaction = viewModel,
            viewModel = viewModel,
            onBackClick = {
                navController.navigateUp()
            },
            navigateToAuth = {
                navController.navigateToAuth()
            }
        )
    }
}

@Composable
private fun ProductDetailsContent(
    state: ProductDetailsUiState,
    viewModel: ProductDetailsViewModel,
    interaction: ProductDetailsInteraction,
    onBackClick: () -> Unit,
    navigateToAuth: () -> Unit,
) {
    Loading(state.isLoading)

    ConnectionErrorPlaceholder(state = state.isConnectionError, onClickTryAgain = {})

    ContentVisibility(state = !state.isLoading && !state.isConnectionError) {
        Scaffold(
            bottomBar = {
                CustomButton(
                    labelIdStringRes = R.string.add_to_cart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(
                            bottom = MaterialTheme.dimens.space56,
                            top = MaterialTheme.dimens.space16,
                            start = MaterialTheme.dimens.space16,
                            end = MaterialTheme.dimens.space16,
                        ),
                    idIconDrawableRes = R.drawable.icon_cart,
                    isEnable = !state.isAddToCartLoading,
                    onClick = {
                        state.product.productId.let {
                            interaction.addProductToCart(
                                it,
                                state.quantity
                            )
                        }
                    }

                )
            }
        ) { padding ->
            Column(Modifier.fillMaxSize()) {

                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (imageProduct, smallImageProduct, info) = createRefs()
                    Box(modifier = Modifier
                        .fillMaxHeight(0.5F)
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
                            state = state,
                            onBackClick = onBackClick,
                            onFavoriteClick = { interaction.onClickFavorite(state.product.productId) },
                        )
                    }

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondary)
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
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.product.productName,
                                style = MaterialTheme.typography.displayMedium.copy(
                                    color = MaterialTheme.colorScheme.onSecondary
                                ),
                            )

                            Row {
                                CustomSmallIconButton(
                                    idIconDrawableRes = R.drawable.icon_remove_from_cart,
                                    background = Color.Transparent,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .border(
                                            1.dp,
                                            MaterialTheme.colorScheme.primary,
                                            CircleShape
                                        ),
                                    iconSize = MaterialTheme.dimens.iconSmall,
                                    onClick = interaction::decreaseProductCount
                                )

                                Text(
                                    text = state.quantity.toString(),
                                    style = MaterialTheme.typography.displayMedium.copy(
                                        color = MaterialTheme.colorScheme.onBackground
                                    ),
                                    modifier = Modifier
                                        .padding(horizontal = MaterialTheme.dimens.space12)
                                )

                                CustomSmallIconButton(
                                    idIconDrawableRes = R.drawable.icon_add_to_cart,
                                    background = MaterialTheme.colorScheme.primary,
                                    iconSize = MaterialTheme.dimens.iconSmall,
                                    onClick = interaction::increaseProductCount
                                )
                            }
                        }
                        SpacerVertical8()
                        TextPrice(state.product.productPrice.toString() + "$")
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.dimens.space24),
                            text = state.product.productDescription,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }

                    SmallProductImages(
                        state = state.smallImages,
                        modifier = Modifier.constrainAs(smallImageProduct) {
                            top.linkTo(imageProduct.bottom)
                            bottom.linkTo(info.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        onClickImage = { index ->
                            interaction.onClickSmallImage(state.smallImages[index])
                        }
                    )
                }
            }
        }
    }
    LaunchedEffect(key1 = state.navigateToAuthGraph) {
        if (state.navigateToAuthGraph.isNavigate) {
            navigateToAuth()
            viewModel.resetNavigation()
        }
    }
}



