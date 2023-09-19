package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography

@Composable
fun CardReviews(
    userName: String,
    reviews: String,
    date: String,
    rating: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = userName,
            style = Typography.displayLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        RatingBar(rating = rating, size = 16.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = reviews,
                    style = Typography.displayLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Text(
                text = date,
                style = Typography.displaySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }
    }
    Divider(
        color = MaterialTheme.colorScheme.onTertiaryContainer,
        modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
        thickness = 1.dp
    )
}


@Preview
@Composable
fun CardReviewsPreview() {
    CardReviews(
        userName = "hhh",
        reviews = "lkkdfdfdfnjkdshfjkdhfjhdskjnbvcmnxbvxnmxcbvhfvgdfhjgjfkgjvncxbmbreui",
        date = "kjhg",
        rating = 1.5f
    )
}