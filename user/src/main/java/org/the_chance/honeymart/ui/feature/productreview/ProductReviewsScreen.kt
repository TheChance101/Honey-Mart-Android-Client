package org.the_chance.honeymart.ui.feature.productreview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
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
import org.the_chance.honymart.ui.composables.ReviewsProgressBar
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.blackOn87

@Composable
fun ProductReviewsScreen(
    viewModel: ProductReviewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
//    ProductReviewsContent()
}


@Composable
fun ProductReviewsContent(
    state: ProductReviewsUiState,
    listener: ProductReviewsInteractionsListener
) {

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
            Icon(
                painter = painterResource(R.drawable.icon_arrow_back),
                contentDescription = "icon back",
                tint = blackOn87
            )
            Text(text = stringResource(R.string.customers_reviews), style = Typography.bodyMedium)
        }

        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + slideInVertically(),
            exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            ReviewsProgressBar(
                starNumber = "5",
                countReview = "13",
                rating = 0.6f
            )
            ReviewsProgressBar(starNumber = "4", countReview = "4", rating = 0f)
            ReviewsProgressBar(starNumber = "3", countReview = "6", rating = 0.1f)
            ReviewsProgressBar(starNumber = "2", countReview = "1", rating = 0.4f)
            ReviewsProgressBar(starNumber = "1", countReview = "1", rating = 0.2f)
        }


    }
}