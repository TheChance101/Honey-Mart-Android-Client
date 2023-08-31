package org.the_chance.honeymart.ui.features.authentication.waitingApprove

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.NavigationHandler
import org.the_chance.honeymart.ui.components.WaitingApprovePlaceholder
import org.the_chance.honeymart.ui.features.authentication.login.navigateToLogin
import org.the_chance.honeymart.ui.features.authentication.signup.SignUpViewModel
import org.the_chance.honeymart.ui.features.authentication.signup.SignupUiEffect
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honymart.ui.theme.white30

@Composable
fun WaitingApproveScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                SignupUiEffect.ClickLoginEffect -> {
                    navController.navigateToLogin()
                }

                else -> {}
            }
        })
    WaitingApproveContent(viewModel)
}

@Composable
private fun WaitingApproveContent(
    listener: SignUpViewModel
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(bottom = 64.dp)
        ) {
            HoneyMartTitle()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.need_approve_from_admin_to_complete_create_market),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
            }
            WaitingApprovePlaceholder(state = true)
        }
        IconButton(
            onClick = { listener.onClickLogout() },
            modifier = Modifier
                .background(white30)
                .size(64.dp)
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "Logout Icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewWaitingApproveScreen() {
    WaitingApproveScreen()
}
