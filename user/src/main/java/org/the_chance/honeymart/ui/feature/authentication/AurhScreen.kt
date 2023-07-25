package org.the_chance.honeymart.ui.feature.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun AuthScreen() {
    AuthContent(onClickSignUp = {})
}

@Composable
fun AuthContent(
    onClickSignUp: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.background_frame_auth),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(R.string.welcome_to_honey_mart),
            modifier = Modifier.padding(vertical = 16.dp),
            color = black60,
            style = Typography.displayMedium
        )
        Text(
            text = stringResource(R.string.get_ready_to_a_shopping_experience_like_no_other),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = black37,
            style = Typography.bodySmall,
            textAlign = TextAlign.Center
        )
        CustomButton(
            labelIdStringRes = R.string.sign_up,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp),
            onClick = onClickSignUp
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(
                text = stringResource(R.string.already_have_account),
                color = black37,
                style = Typography.displaySmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.log_in),
                color = primary100,
                style = Typography.displayLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthPrev() {
    AuthContent(onClickSignUp = {})
}