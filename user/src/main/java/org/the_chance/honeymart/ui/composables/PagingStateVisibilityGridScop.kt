import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

fun <T : Any> LazyGridScope.pagingStateVisibilityGridScope(
    products: LazyPagingItems<T>,
) {
    when {
        products.loadState.refresh is LoadState.Loading -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(MaterialTheme.dimens.space64)
                    )
                }
            }
        }

        products.loadState.append is LoadState.Loading -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Loading(
                        modifier = Modifier.padding(MaterialTheme.dimens.space12).align(Alignment.Center),
                        size = MaterialTheme.dimens.space64,
                        state = true
                    )
                }
            }
        }
    }
}