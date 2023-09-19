package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography

@Composable
fun AverageRating(
    averageRating: Float,
    reviewCount: String,
) {
    val formattedRating = String.format("%.1f", averageRating)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formattedRating,
            style = Typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RatingBar(rating = averageRating, totalStars = 5, size = 20.dp)
            Text(
                text = "$reviewCount Ratings",
                style = Typography.displaySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}
//
//@Preview()
//@Composable
//fun mmm() {
//    AverageRating(averageRating = "3.5", reviewCount = "23 Rating", rating = 2.5f)
//}