package org.the_chance.honeymart.ui.features.category.content

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.FormHeader
import org.the_chance.honeymart.ui.components.FormTextField
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.composable.AddImageButton
import org.the_chance.honeymart.ui.features.category.composable.ItemImageProduct
import org.the_chance.honeymart.ui.features.category.composable.ItemImageProductDetails
import org.the_chance.honeymart.ui.features.category.showProductUpdateContent
import org.the_chance.honeymart.ui.features.category.showSaveUpdateButton
import org.the_chance.honeymart.ui.util.defaultTo1IfZero
import org.the_chance.honeymart.ui.util.handleImageSelection
import org.the_chance.honymart.ui.composables.AverageRating
import org.the_chance.honymart.ui.composables.CardReviews
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyOutlineButton
import org.the_chance.honymart.ui.composables.ReviewsProgressBar
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductDetailsContent(
    titleScreen: String,
    confirmButton: String,
    cancelButton: String,
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
    onClickConfirm: () -> Unit,
    onClickCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGES),
        onResult = { it.handleImageSelection(context, state, listener::onImagesSelected) }
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.onTertiary,
                shape = MaterialTheme.shapes.medium
            )
            .verticalScroll(rememberScrollState())
    ) {

        item {

            FormHeader(
                title = titleScreen,
                iconPainter = painterResource(id = R.drawable.icon_add_product)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
            ) {
                FormTextField(
                    text = state.productDetails.productNameState.name,
                    hint = stringResource(R.string.product_name),
                    keyboardType = KeyboardType.Text,
                    onValueChange = listener::onUpdateProductName,
                    errorMessage = state.productDetails.productNameState.errorState,
                    isEnable = !state.showScreenState.showProductDetails
                )
                FormTextField(
                    text = state.productDetails.productPriceState.name,
                    hint = stringResource(R.string.price),
                    keyboardType = KeyboardType.Number,
                    onValueChange = listener::onUpdateProductPrice,
                    errorMessage = state.productDetails.productPriceState.errorState,
                    isEnable = !state.showScreenState.showProductDetails
                )
                FormTextField(
                    text = state.productDetails.productDescriptionState.name,
                    hint = stringResource(R.string.description),
                    keyboardType = KeyboardType.Text,
                    onValueChange = listener::onUpdateProductDescription,
                    errorMessage = state.productDetails.productDescriptionState.errorState,
                    isEnable = !state.showScreenState.showProductDetails
                )
            }
            Text(
                modifier = Modifier.padding(
                    top = MaterialTheme.dimens.space24,
                    start = MaterialTheme.dimens.space16
                ),
                text = stringResource(R.string.product_image),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center,
            )

            Row(
                modifier = Modifier
                    .height(256.dp)
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.space16)
            ) {
                if (state.showScreenState.showProductDetails) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(MaterialTheme.dimens.card),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                    ) {
                        items(items = state.productDetails.productImage) { image ->
                            ItemImageProductDetails(image = image)
                        }
                    }
                } else if (state.showScreenState.showProductUpdate) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(MaterialTheme.dimens.card),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                    ) {
                        items(items = state.newProducts.images) { image ->
                            ItemImageProduct(image, listener::onClickRemoveSelectedImage)
                        }
                        if (state.newProducts.images.size < MAX_IMAGES) {
                            item {
                                AddImageButton(multiplePhotoPickerLauncher)
                            }
                        }
                    }
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(56.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = stringResource(R.string.customers_reviews),
                    style = Typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
        item {
            AnimatedVisibility(
                visible = !state.isLoading,
                enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + slideInVertically(),
                exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutHorizontally()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    AverageRating(
                        averageRating = state.reviews.reviewStatisticUiState.averageRating.toFloat(),
                        reviewCount = state.reviews.reviewStatisticUiState.reviewCount.toString(),
                    )

                    ReviewsProgressBar(
                        starNumber = "5",
                        countReview = state.reviews.reviewStatisticUiState.fiveStarsCount.toString(),
                        rating =
                        (state.reviews.reviewStatisticUiState.fiveStarsCount.toFloat() /
                                state.reviews.reviewStatisticUiState.reviewCount.defaultTo1IfZero()
                                )
                    )

                    ReviewsProgressBar(
                        starNumber = "4",
                        countReview = state.reviews.reviewStatisticUiState.fourStarsCount.toString(),
                        rating =
                        (state.reviews.reviewStatisticUiState.fourStarsCount.toFloat() /
                                state.reviews.reviewStatisticUiState.reviewCount
                                    .defaultTo1IfZero())
                    )
                    ReviewsProgressBar(
                        starNumber = "3",
                        countReview = state.reviews.reviewStatisticUiState.threeStarsCount.toString(),
                        rating = (state.reviews.reviewStatisticUiState.threeStarsCount.toFloat() /
                                state.reviews.reviewStatisticUiState.reviewCount
                                    .defaultTo1IfZero())
                    )
                    ReviewsProgressBar(
                        starNumber = "2",
                        countReview = state.reviews.reviewStatisticUiState.twoStarsCount.toString(),
                        rating = (state.reviews.reviewStatisticUiState.twoStarsCount.toFloat() /
                                state.reviews.reviewStatisticUiState.reviewCount
                                    .defaultTo1IfZero())
                    )
                    ReviewsProgressBar(
                        starNumber = "1",
                        countReview = state.reviews.reviewStatisticUiState.oneStarCount.toString(),
                        rating = (state.reviews.reviewStatisticUiState.oneStarCount.toFloat() /
                                state.reviews.reviewStatisticUiState.reviewCount
                                    .defaultTo1IfZero())
                    )
                }
            }
        }

        items(state.reviews.reviews.size) { position ->
//            onChangeReviews(position)
//            if ((position + 1) >= (state.page * MAX_PAGE_SIZE)) {
//                listener.onScrollDown()
//            }
            val review = state.reviews.reviews[position]
            CardReviews(
                userName = review.fullName,
                rating = review.rating.toFloat(),
                reviews = review.content,
                date = review.reviewDate
            )
        }
    }

    Box(contentAlignment = Alignment.BottomEnd) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
        ) {
            HoneyFilledButton(
                modifier = Modifier.width(146.dp),
                label = confirmButton,
                onClick = onClickConfirm,
                isButtonEnabled = if (state.showProductUpdateContent())
                    state.showSaveUpdateButton() else true
            )
            HoneyOutlineButton(onClick = onClickCancel, label = cancelButton)
        }
    }
}