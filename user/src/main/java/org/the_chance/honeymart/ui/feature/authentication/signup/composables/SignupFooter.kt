package org.the_chance.honeymart.ui.feature.authentication.signup.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.authentication.signup.SignupInteractionListener
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun SignupFooter(listener: SignupInteractionListener){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = MaterialTheme.dimens.space32)
    ) {
        Text(
            text = stringResource(R.string.already_have_account),
            style = Typography.displaySmall.copy(black37),
            textAlign = TextAlign.Center
        )
        TextButton(
            onClick = listener::onClickLogin,
            colors = ButtonDefaults.textButtonColors(Color.Transparent)
        ) {
            Text(
                text = stringResource(R.string.log_in),
                style = Typography.displayLarge.copy(primary100),
                textAlign = TextAlign.Center,
            )
        }
    }
}