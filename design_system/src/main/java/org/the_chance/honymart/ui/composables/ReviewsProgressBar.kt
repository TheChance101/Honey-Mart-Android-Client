package org.the_chance.honymart.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.lightGrey
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun ReviewsProgressBar(
    starNumber: String,
    countReview: String,
    rating: Float,
) {

    var progress by remember { mutableStateOf(0f) }
    val size by animateFloatAsState(
        targetValue = progress,
        tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = starNumber,
            style = Typography.displaySmall,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Icon(
            painter = painterResource(id = R.drawable.star_7),
            contentDescription = "icon star",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    lightGrey
                )
        ) {

        Box(
                modifier = Modifier
                    .fillMaxWidth(size)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(primary100)
                    .animateContentSize()
            )
        }
        Text(
            text = countReview,
            style = Typography.displaySmall,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }

    LaunchedEffect(key1 = true) {
        progress = rating
    }
}

@Preview
@Composable
fun jjj() {
    Column {
        ReviewsProgressBar(starNumber = "5", countReview = "13", rating = 0.5f)
        ReviewsProgressBar(starNumber = "4", countReview = "13", rating = 0.5f)
        ReviewsProgressBar(starNumber = "3", countReview = "13", rating = 0.5f)
        ReviewsProgressBar(starNumber = "2", countReview = "13", rating = 0.5f)
        ReviewsProgressBar(starNumber = "1", countReview = "13", rating = 0.5f)
    }
}

