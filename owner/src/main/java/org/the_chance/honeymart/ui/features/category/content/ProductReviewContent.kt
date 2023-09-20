package org.the_chance.honeymart.ui.features.category.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.util.defaultTo1IfZero
import org.the_chance.honymart.ui.composables.AverageRating
import org.the_chance.honymart.ui.composables.CardReviews
import org.the_chance.honymart.ui.composables.ReviewsProgressBar
import org.the_chance.honymart.ui.theme.Typography


@Composable
fun ProductReviewContent(
    state: CategoriesUiState,
) {
    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, start = 16.dp, bottom = 8.dp)
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
}

