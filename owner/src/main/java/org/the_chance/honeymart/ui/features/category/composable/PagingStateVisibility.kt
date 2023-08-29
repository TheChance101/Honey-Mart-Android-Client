package org.the_chance.honeymart.ui.features.category.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun <T : Any> PagingStateVisibility(
    items: LazyPagingItems<T>,
    listener: CategoriesInteractionsListener
) {
    when {
        items.loadState.refresh is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Loading(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.space12)
                        .align(Alignment.Center),
                    size = MaterialTheme.dimens.space64,
                    state = true
                )
            }
        }

        items.loadState.append is LoadState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Loading(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.space12)
                        .align(Alignment.Center),
                    size = MaterialTheme.dimens.space64,
                    state = true
                )
            }
        }

        items.loadState.append is LoadState.Error -> {
//            Text(
//                text = stringResource(id = R.string.error_loading_data),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(MaterialTheme.dimens.space16),
//                textAlign = TextAlign.Center
//            )
//            HoneyFilledButton(
//                label = stringResource(id = R.string.try_again_text),
//                onClick = { items.retry() },
//                modifier = Modifier.wrapContentWidth(),
//            )

            listener.onErrorProducts()
        }
    }
}