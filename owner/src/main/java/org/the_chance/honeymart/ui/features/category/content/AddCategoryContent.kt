package org.the_chance.honeymart.ui.features.category.content

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.FormHeader
import org.the_chance.honeymart.ui.components.FormTextField
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.composable.CategoryIconItem
import org.the_chance.honeymart.ui.features.category.showAddUpdateCategoryButton
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.theme.dimens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddCategoryContent(
    listener: CategoriesInteractionsListener,
    state: CategoriesUiState,
) {
    Scaffold(
        topBar = {
            FormHeader(
                title = stringResource(R.string.add_new_category),
                iconPainter = painterResource(id = R.drawable.icon_add_new_category)
            )
        },
        bottomBar = {
            HoneyFilledButton(
                label = stringResource(R.string.add),
                onClick = {
                    listener.onClickAddCategory(
                        name = state.newCategory.categoryState.name,
                        categoryIconID = state.newCategory.newIconId
                    )
                },
                isButtonEnabled = state.showAddUpdateCategoryButton(),
                isLoading = state.isLoading,
                icon = R.drawable.icon_add_product,
                modifier = Modifier
                    .padding(
                        vertical = MaterialTheme.dimens.space16,
                    )
            )
        },
        modifier = Modifier.clip(
            shape = MaterialTheme.shapes.medium
        ),
        containerColor = MaterialTheme.colorScheme.onTertiary,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = MaterialTheme.dimens.categoryIconItem),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                contentPadding = PaddingValues(MaterialTheme.dimens.space16)
            ) {

                item(span = { GridItemSpan(maxLineSpan) }) {
                    FormTextField(
                        text = state.newCategory.categoryState.name,
                        modifier = Modifier.padding(0.dp),
                        hint = stringResource(R.string.category_name),
                        keyboardType = KeyboardType.Text,
                        onValueChange = listener::onNewCategoryNameChanged,
                        errorMessage =  state.newCategory.categoryState.errorState
                    )
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        modifier = Modifier
                            .padding(
                                top = MaterialTheme.dimens.space32,
                                bottom = MaterialTheme.dimens.space8
                            )
                            .align(Alignment.Start),
                        text = stringResource(R.string.select_category_image),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                    )
                }

                items(count = state.categoryIcons.size) { index ->
                    CategoryIconItem(
                        iconPainter = painterResource(id = state.categoryIcons[index].icon),
                        isSelected = state.categoryIcons[index].isSelected,
                        categoryIconId = state.categoryIcons[index].categoryIconId,
                        onClick = {
                            listener.onClickNewCategoryIcon(
                                state.categoryIcons[index].categoryIconId
                            )
                        }
                    )
                }
            }
        }
    }
}


