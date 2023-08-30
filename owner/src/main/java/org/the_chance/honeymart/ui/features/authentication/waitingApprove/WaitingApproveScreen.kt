package org.the_chance.honeymart.ui.features.authentication.waitingApprove

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.components.WaitingApprovePlaceholder
import org.the_chance.honeymart.ui.features.authentication.signup.SignUpViewModel
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.design_system.R

@Composable
fun WaitingApproveScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    WaitingApproveContent(viewModel)
}

@Composable
private fun WaitingApproveContent(
    listener: SignUpViewModel
) {
    Box(Modifier.fillMaxSize()) {
        Column {
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
            modifier = Modifier.size(64.dp).align(Alignment.BottomStart)
        ) {
            Icon(
                modifier = Modifier.clip(CircleShape).padding(16.dp),
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "Logout Icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
