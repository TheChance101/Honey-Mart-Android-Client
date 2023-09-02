package org.the_chance.honeymart.ui.feature.authentication.signup.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun AuthScaffold(
    title: String,
    description: String,
    modifier: Modifier = Modifier
){
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(R.drawable.background_frame),
            contentDescription = "",
            modifier = modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space24),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
        ) {
            Text(
                text = title,
                color = white,
                style = Typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = description,
                color = white,
                style = Typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}