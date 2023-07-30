package org.the_chance.honeymart.ui.feature.cart.Composeables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.airbnb.lottie.compose.LottieCompositionSpec
//import com.airbnb.lottie.compose.LottieConstants
//import com.airbnb.lottie.compose.animateLottieCompositionAsState
//import com.airbnb.lottie.compose.rememberLottieComposition
//import org.the_chance.design_system.R
//
//@SuppressLint("RememberReturnType")
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Loading(){
//    val composition by rememberLottieComposition(
//        spec = LottieCompositionSpec.RawRes(R.raw.loading)
//    )
//    val animationSpeed by remember { mutableStateOf(1f) }
//    val progress by animateLottieCompositionAsState(
//        composition = composition,
//        restartOnPlay = false,
//        iterations = LottieConstants.IterateForever,
//        speed = animationSpeed
//    )
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ){
//        com.airbnb.lottie.compose.LottieAnimation(
//            composition,
//            progress,
//            modifier = Modifier.size(100.dp),
//
//        )
//    }
//}
//
