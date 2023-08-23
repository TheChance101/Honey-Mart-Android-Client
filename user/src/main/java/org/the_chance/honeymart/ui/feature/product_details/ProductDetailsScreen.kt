package org.the_chance.honeymart.ui.feature.product_details


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.feature.authentication.navigateToAuth
import org.the_chance.honeymart.ui.feature.product_details.composeable.ProductAppBar
import org.the_chance.honeymart.ui.feature.product_details.composeable.SmallProductImages
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.composables.HoneyIconButton
import org.the_chance.honymart.ui.composables.HoneyOutlineText
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.SnackBarWithDuration
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is ProductDetailsUiEffect.AddProductToWishListEffectError -> {}
                is ProductDetailsUiEffect.AddToCartError -> {}
                is ProductDetailsUiEffect.AddToCartSuccess -> {viewModel.showSnackBar(it.message)}
                ProductDetailsUiEffect.OnBackClickEffect -> navController.navigateUp()
                is ProductDetailsUiEffect.ProductNotInSameCartMarketExceptionEffect ->
                {viewModel.showDialog(it.productId ,it.count)}
                ProductDetailsUiEffect.UnAuthorizedUserEffect -> navController.navigateToAuth()
            }
        }
    }
    ProductDetailsContent(state = state, listener = viewModel)
}

@Composable
private fun ProductDetailsContent(
    state: ProductDetailsUiState,
    listener: ProductDetailsInteraction,

    ) {
    Loading(state.isLoading)

    ConnectionErrorPlaceholder(
        state = state.isConnectionError,
        onClickTryAgain = listener::onclickTryAgain
    )

    ContentVisibility(state = state.contentScreen()) {
        Scaffold(
            bottomBar = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    HoneyFilledIconButton(
                        label = stringResource(id = R.string.add_to_cart),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 8.dp)
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .padding(
                                 bottom = MaterialTheme.dimens.space56,
                                top = MaterialTheme.dimens.space16,
                                start = MaterialTheme.dimens.space16,
                                end = MaterialTheme.dimens.space16,
                            )
                            .align(Alignment.BottomCenter),
                        iconPainter = painterResource(id = R.drawable.icon_cart),
                        isEnable = !state.isAddToCartLoading,
                        onClick = {
                            state.product.productId.let {
                                listener.addProductToCart(
                                    it,
                                    state.quantity
                                )
                            }
                        }
                    )
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .padding(bottom = MaterialTheme.dimens.space16)
                    ) {
                        Loading(
                            state = state.isAddToCartLoading,
                            size = 75.dp,
                            modifier = Modifier
                        )
                    }
                    Box(modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 120.dp)) {
                        AnimatedVisibility(
                            visible = state.snackBar.isShow,
                            enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + slideInVertically(),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutHorizontally()
                        ) {
                            SnackBarWithDuration(
                                message = state.snackBar.massage,
                                onDismiss = listener::resetSnackBarState,
                                undoAction = {},
                                text = ""
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        if (state.dialogState.showDialog) {
                            CustomAlertDialog(
                                message = stringResource
                                    (R.string.add_from_different_cart_message),
                                onConfirm = {
                                    listener.confirmDeleteLastCartAndAddProductToNewCart(
                                        state.dialogState.productId, state.dialogState.count
                                    )
                                    listener.resetDialogState()
                                },
                                onCancel = { listener.resetDialogState() },
                                onDismissRequest = { listener.resetDialogState() }
                            )
                        }

                    }
                }
            }
        )
        { padding ->
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

                        ProductAppBar(
                            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16),
                            state = state,
                            onBackClick = listener::onClickBack,
                            onFavoriteClick = { listener.onClickFavorite(state.product.productId) },
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
                                HoneyIconButton(
                                    iconPainter = painterResource(id = R.drawable.icon_remove_from_cart),
                                    background = Color.Transparent,
                                    isLoading = state.isAddToCartLoading,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .border(
                                            1.dp,
                                            MaterialTheme.colorScheme.primary,
                                            CircleShape
                                        ),
                                    onClick = listener::decreaseProductCount,
                                )

                                Text(
                                    text = state.quantity.toString(),
                                    style = MaterialTheme.typography.displayMedium.copy(
                                        color = MaterialTheme.colorScheme.onBackground
                                    ),
                                    modifier = Modifier
                                        .padding(horizontal = MaterialTheme.dimens.space12)
                                )

                                HoneyIconButton(
                                    iconPainter = painterResource(id = R.drawable.icon_add_to_cart),
                                    background = MaterialTheme.colorScheme.primary,
                                    isLoading = state.isAddToCartLoading,
                                    onClick = listener::increaseProductCount,
                                )
                            }
                        }

                        HoneyOutlineText(
                            modifier = Modifier.padding(vertical = MaterialTheme.dimens.space8),
                            state.totalPrice.toString() + "$",
                        )
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
                            listener.onClickSmallImage(state.smallImages[index])
                        }
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewScreen() {
    ProductDetailsScreen()
}