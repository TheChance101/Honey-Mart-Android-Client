package org.the_chance.honeymart.ui.feature.cart.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.cart.CartContent
import org.the_chance.honeymart.ui.feature.cart.CartViewModel
import org.the_chance.honeymart.ui.feature.cart.Composeables.CartPlaceholder
import org.the_chance.honeymart.ui.feature.uistate.CartUiState
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.white

//@Composable
//fun BottomSheetCompleteOrder(
//    state: CartUiState,
//    onClickPlusOrder: () -> Unit,
//    onClickMinusOrder: () -> Unit
//){
//    BottomSheetCompleteOrderContent(state,onClickPlusOrder, onClickMinusOrder)
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCompleteOrderContent(
    state: CartUiState,
    onClickPlusOrder: (Long?) -> Unit,
    onClickMinusOrder: (Long?) -> Unit,
){
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { it != SheetValue.PartiallyExpanded },
        skipPartiallyExpanded = false
    )
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .width(217.dp)
                        .height(163.dp),
                    painter = painterResource(id = R.drawable.complete_cart_order),
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier.padding(vertical = 24.dp, horizontal = 34.dp),
                    text = stringResource(id = R.string.order_complete),
                    textAlign = TextAlign.Center,
                    style = Typography.bodySmall.copy(color = black37)
                )

                CustomButton(
                    labelIdStringRes = R.string.show_my_order_details,
                    modifier = Modifier
                        .padding(start = 36.dp, end = 36.dp, bottom = 24.dp),
                ){}
            }
        },
        sheetContainerColor = white,
        sheetPeekHeight = 0.dp
    ) {
        CartContent(
            state = state,
            sheetState = sheetState,
            onClickPlusOrder = {
                onClickPlusOrder(it)
                               },
            onClickMinusOrder = {
                onClickMinusOrder(it)
            }

        )
    }
}
