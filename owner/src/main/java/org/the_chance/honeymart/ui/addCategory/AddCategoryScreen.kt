package org.the_chance.honeymart.ui.addCategory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.addCategory.composable.CategoryImage
import org.the_chance.honeymart.ui.addCategory.composable.HeaderText
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.blackOn37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
//    AddCategoryContent(listener = viewModel,state = state)
}

@Composable
fun AddCategoryContent(
    listener: CategoriesInteractionsListener,
    state: CategoriesUiState,
    showAddCategory: Boolean,
) {
    AnimatedVisibility(
        visible = !state.isError,
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topEnd = MaterialTheme.dimens.space16,
                    topStart = MaterialTheme.dimens.space16
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(white)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_honey_sun),
                contentDescription = "",
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Column(modifier = Modifier.fillMaxSize()) {
                HeaderText(title = stringResource(R.string.add_new_category))

                HoneyTextField(
                    text = state.nameCategory,
                    modifier = Modifier.padding(top = MaterialTheme.dimens.space64),
                    hint = stringResource(R.string.category_name),
                    onValueChange = listener::changeNameCategory
                )

                Text(
                    modifier = Modifier.padding(
                        start = MaterialTheme.dimens.space16,
                        top = MaterialTheme.dimens.space32
                    ),
                    text = stringResource(R.string.select_category_image),
                    style = Typography.bodyMedium.copy(color = blackOn37)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space24)
                ) {
                    items(count = state.categoryImages.size) { index ->
                        CategoryImage(
                            iconPainter = painterResource(id = state.categoryImages[index].image),
                            isSelected = state.categoryImages[index].isSelected,
                            categoryImageID = state.categoryImages[index].categoryImageId,
                            onClick = {
                                listener.onClickCategoryImage(
                                    state.categoryImages[index].categoryImageId
                                )
                            }
                        )
                    }
                }

            }
            HoneyFilledIconButton(
                label = stringResource(R.string.add),
                onClick = {
                    listener.onClickAddCategory()
                },
                isEnable = !state.isLoading,
                iconPainter = painterResource(id = R.drawable.icon_add_product),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = MaterialTheme.dimens.space64,
                        start = MaterialTheme.dimens.space24,
                        end = MaterialTheme.dimens.space24
                    )
            )
        }
    }


    Loading(state = state.isLoading)

}
