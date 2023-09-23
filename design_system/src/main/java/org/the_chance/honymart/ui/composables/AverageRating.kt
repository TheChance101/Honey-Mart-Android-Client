package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Typography

@Composable
fun AverageRating(
    averageRating: Float,
    reviewCount: String,
) {
    val formattedRating = String.format("%.1f", averageRating)
    val ratingText = stringResource(R.string.ratings, reviewCount)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 12.dp),
            text = formattedRating,
            style = Typography.headlineMedium.copy(MaterialTheme.colorScheme.onSecondary),
            textAlign = TextAlign.Center,
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RatingBar(rating = averageRating, totalStars = 5, size = 20.dp)
            Text(
                text = ratingText,
                style = Typography.displaySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AverageRatingPreview() {
    AverageRating(averageRating = 3.5F, reviewCount = "23 Rating")
}