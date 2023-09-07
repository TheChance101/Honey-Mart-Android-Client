package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun PermissionDialog(
    onClickGoToSettings: () -> Unit,
    onClickDismiss: () -> Unit,
    onDismissRequest: () -> Unit,
    message: String,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier,
            shape = Shapes.extraLarge,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onTertiary)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_warning),
                    contentDescription = stringResource(R.string.warning_icon),
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.dimens.space32),
                )
                Text(
                    text = message,
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.space16),
                    color = black60,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Center
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.dimens.space16,
                            vertical = MaterialTheme.dimens.space32
                        )
                ) {
                    TextButton(
                        modifier = Modifier
                            .width(144.dp)
                            .height(MaterialTheme.dimens.heightPrimaryButton)
                            .padding(end = MaterialTheme.dimens.space8),
                        onClick = onClickGoToSettings,
                        colors = ButtonDefaults.textButtonColors(primary100),
                        shape = Shapes.medium,
                    ) {
                        Text(
                            text = stringResource(R.string.go_to_settings),
                            style = Typography.displayLarge.copy(color = white)
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .width(144.dp)
                            .height(MaterialTheme.dimens.heightPrimaryButton),
                        onClick = onClickDismiss,
                        colors = ButtonDefaults.textButtonColors(Color.Transparent),
                        shape = Shapes.medium,
                        border = BorderStroke(MaterialTheme.dimens.strokeNormal, color = black16)
                    )
                    {
                        Text(
                            text = stringResource(R.string.dismiss),
                            style = Typography.displayLarge.copy(color = MaterialTheme.colorScheme.onTertiaryContainer)
                        )
                    }
                }
            }
        }
    }
}


