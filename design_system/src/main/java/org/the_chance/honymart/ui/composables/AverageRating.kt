package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37

@Composable
fun AverageRating(
    averageRating: String,
    reviewCount: String,
    rating: Float
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        Text(
            text = averageRating,
            style = Typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RatingBar(rating = rating, totalStars = 5)
            Text(
                text = reviewCount,
                style = Typography.displaySmall,
                color = black37
            )
        }
    }
}

@Preview()
@Composable
fun mmm() {
    AverageRating(averageRating = "3.5", reviewCount = "23 Rating", rating = 2.5f)
}