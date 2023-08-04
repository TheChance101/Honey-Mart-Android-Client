package org.the_chance.honeymart.ui.feature.login.compsoables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>
) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUi(modifier)
    }
}

@Composable
fun CustomDialogUi(
    modifier: Modifier = Modifier,
) {
    Card(
        shape = Shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .background(Color.White)
                .padding(MaterialTheme.dimens.space16)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(R.drawable.placeholder_done),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(138.dp)
                    .padding(bottom = MaterialTheme.dimens.space24),
            )
            Text(
                text = stringResource(R.string.success_sign_up_description),
                style = Typography.bodySmall.copy(black37),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun MyDialogUIPreview(){
    CustomDialogUi()
}