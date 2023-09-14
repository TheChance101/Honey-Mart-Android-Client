package org.the_chance.honeymart.ui.feature.productreview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.AverageRating
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.composables.ReviewsProgressBar
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.blackOn87

@Composable
fun ProductReviewsScreen(
    viewModel: ProductReviewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ProductReviewsContent(state = state, listener = viewModel)
}

@Composable
fun ProductReviewsContent(
    state: ProductReviewsUiState,
    listener: ProductReviewsInteractionsListener
) {
//    val reviews = state.reviews.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = listener::onClickBack) {
                Icon(
                    painter = painterResource(R.drawable.icon_arrow_back),
                    contentDescription = "icon back",
                    tint = blackOn87
                )
            }
            Text(text = stringResource(R.string.customers_reviews), style = Typography.bodyMedium)
        }

        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + slideInVertically(),
            exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            AverageRating(
                averageRating = state.reviews.reviewStatisticUiState.averageRating.toString(),
                reviewCount = state.reviews.reviewStatisticUiState.reviewCount.toString(),
                rating = state.reviews.reviewStatisticUiState.averageRating.toFloat()
            )

            Box(contentAlignment = Alignment.Center) {
                ReviewsProgressBar(
                    starNumber = "5",
                    countReview = state.reviews.reviewStatisticUiState.fiveStarsCount.toString(),
                    rating =
                    (state.reviews.reviewStatisticUiState.fiveStarsCount / state.reviews.reviewStatisticUiState.reviewCount).toFloat()
                )
                ReviewsProgressBar(
                    starNumber = "4",
                    countReview = state.reviews.reviewStatisticUiState.fourStarsCount.toString(),
                    rating = (state.reviews.reviewStatisticUiState.fiveStarsCount / state.reviews.reviewStatisticUiState.reviewCount).toFloat()
                )
                ReviewsProgressBar(
                    starNumber = "3",
                    countReview = state.reviews.reviewStatisticUiState.threeStarsCount.toString(),
                    rating = (state.reviews.reviewStatisticUiState.threeStarsCount / state.reviews.reviewStatisticUiState.reviewCount).toFloat()
                )
                ReviewsProgressBar(
                    starNumber = "2",
                    countReview = state.reviews.reviewStatisticUiState.twoStarsCount.toString(),
                    rating = (state.reviews.reviewStatisticUiState.twoStarsCount / state.reviews.reviewStatisticUiState.reviewCount).toFloat()
                )
                ReviewsProgressBar(
                    starNumber = "1",
                    countReview = state.reviews.reviewStatisticUiState.oneStarCount.toString(),
                    rating = (state.reviews.reviewStatisticUiState.oneStarCount / state.reviews.reviewStatisticUiState.reviewCount).toFloat()
                )
            }
//
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(color = MaterialTheme.colorScheme.background)
//                    .padding(
//                        top = MaterialTheme.dimens.space8,
//                        start = MaterialTheme.dimens.space8,
//                        end = MaterialTheme.dimens.space16
//                    )
//            ) {
//                items(reviews.itemCount) { position ->
//                    val review = reviews[position]
//                    if (review != null) {
//                        CardReviews(
//                            userName = review.fullName ?: "",
//                            rating = review.rating.toFloat(),
//                            reviews = "",
//                            data = review.reviewDate.toString()
//                        )
//                    }
//                }
//            }
        }
    }
}
