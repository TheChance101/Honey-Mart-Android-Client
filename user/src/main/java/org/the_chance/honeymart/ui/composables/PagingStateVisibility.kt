package org.the_chance.honeymart.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

fun <T : Any> LazyListScope.PagingStateVisibility(
    products: LazyPagingItems<T>,
    onClickTryAgain: () -> Unit
) {
    when {
        products.loadState.refresh is LoadState.Loading -> {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Loading(
                        modifier = Modifier.padding(MaterialTheme.dimens.space12).align(Alignment.Center),
                        size = MaterialTheme.dimens.space64,
                        state = true
                    )
                }
            }
        }

        products.loadState.append is LoadState.Loading -> {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Loading(
                        modifier = Modifier.padding(MaterialTheme.dimens.space12).align(Alignment.Center),
                        size = MaterialTheme.dimens.space64,
                        state = true
                    )
                }
            }
        }

        products.loadState.refresh is LoadState.Error || products.loadState.append is LoadState.Error -> {
            item {
                Text(
                    text = stringResource(id = R.string.error_loading_data),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.space16),
                    textAlign = TextAlign.Center
                )
                HoneyFilledButton(
                    label = stringResource(id = R.string.try_again_text),
                    onClick = onClickTryAgain,
                    modifier = Modifier.wrapContentWidth(),
                )
            }
        }
    }
}