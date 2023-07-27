package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.the_chance.design_system.R


@Composable
fun Loading() {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading)
    )
    LottieAnimation(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}