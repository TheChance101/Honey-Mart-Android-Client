package org.the_chance.honeymart.ui.feature.cart.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BottomSheetCompleteOrderContent(
    onClick: () -> Unit = {},
    state: Boolean = false,
) {
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { it != SheetValue.PartiallyExpanded },
        skipPartiallyExpanded = false
    )
    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.Transparent,
        sheetState = ModalBottomSheetState(
            if (sheetState.isVisible) ModalBottomSheetValue.valueOf(sheetState.targetValue.name)
            else ModalBottomSheetValue.Hidden
        ),
        scrimColor = Color.Black.copy(alpha = 0.32f),
        content = { LaunchedEffect(key1 = state) { if (state) sheetState.expand() else sheetState.hide() } },
        sheetContent = {
            BottomSheetContent(onClick = onClick)
        },
    )
}

@Preview
@Composable
private fun BottomSheetContent(
    onClick: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(
            topStart = MaterialTheme.dimens.space28,
            topEnd = MaterialTheme.dimens.space28
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                painter = painterResource(id = R.drawable.drag), contentDescription = "",
                modifier = Modifier.padding(top = MaterialTheme.dimens.space12)
            )
            Image(
                modifier = Modifier
                    .width(217.dp)
                    .height(163.dp)
                    .padding(top = 16.dp),
                painter = painterResource(id = R.drawable.complete_cart_order),
                contentDescription = ""
            )

            Text(
                modifier = Modifier.padding(
                    vertical = MaterialTheme.dimens.space24,
                    horizontal = MaterialTheme.dimens.space34
                ),
                text = stringResource(id = R.string.order_complete),
                textAlign = TextAlign.Center,
                style = Typography.bodySmall.copy(color = black37)
            )

            CustomButton(
                labelIdStringRes = R.string.show_my_order_details,
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.dimens.space36,
                        end = MaterialTheme.dimens.space36,
                        bottom = MaterialTheme.dimens.space24
                    ),
                onClick = onClick,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}

