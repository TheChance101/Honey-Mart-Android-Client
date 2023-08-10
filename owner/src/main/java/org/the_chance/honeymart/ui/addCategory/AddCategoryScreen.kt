package org.the_chance.honeymart.ui.addCategory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.the_chance.honeymart.ui.addCategory.composable.CategoryImage
import org.the_chance.honeymart.ui.addCategory.composable.HeaderText
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.owner_black637
import org.the_chance.honymart.ui.theme.white
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.addCategory.composable.CategoryItem
import org.the_chance.honeymart.ui.addCategory.composable.EmptyCategory
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.black87
import org.the_chance.honymart.ui.theme.blackOn87
import org.the_chance.honymart.ui.theme.darkPrimary100
import org.the_chance.honymart.ui.theme.owner_black60
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    AddCategoryContent(listener = viewModel,state = state)
}

@Composable
private fun AddCategoryContent(listener:AddCategoryListener, state: AddCategoryUIState){

    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = state.categories != emptyList<CategoryUIState>(),
            modifier = Modifier.weight(1f)
        ) {
            Column(modifier = Modifier.fillMaxSize()){
                HoneyMartTitle()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space32 )
                ){
                    items(count=state.categories.size ){index ->
                        CategoryItem(
                            categoryName = state.categories[index].categoryName,
                            onCategoryClicked = {_ , _ -> },
                            position = 0,
                            image = categoryImages.find {
                                it.categoryImageId == state.categories[index].categoryIcon
                            }?.categoryImage ?: R.drawable.icon_category
                        )
                    }
                    item {
                        CategoryItem(
                            categoryName = "Add",
                            onCategoryClicked = {_ , _ -> },
                            position = 0,
                            image = R.drawable.icon_add_product
                        )
                    }
                }
            }
        }

        EmptyCategory(
            state = state.categories == emptyList<CategoryUIState>(),
            modifier = Modifier.weight(1f)
        )

        AnimatedVisibility(
            visible = !state.isError,
            modifier = Modifier
                .weight(1f)
                .padding(top = MaterialTheme.dimens.space112, end = MaterialTheme.dimens.space16)
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
                    .background(white)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_honey_sun),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.TopEnd)
                )

                Column(modifier = Modifier.fillMaxSize()) {
                    HeaderText(title = "Add New category")

                    HoneyTextField(
                        text = state.nameCategory,
                        modifier = Modifier.padding(top = MaterialTheme.dimens.space64),
                        hint = "Category Name",
                        onValueChange =  listener::changeNameCategory
                    )

                    Text(
                        modifier = Modifier.padding(
                            start = MaterialTheme.dimens.space16,
                            top = MaterialTheme.dimens.space32
                        ),
                        text = "Select category image",
                        style = Typography.bodyMedium.copy(color = owner_black637)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space24 )
                    ){
                        items(count=state.categoryImages.size ){index ->
                            CategoryImage(
                                iconPainter = painterResource(id = state.categoryImages[index].image),
                                isSelected = state.categoryImages[index].isSelected,
                                categoryImageID = state.categoryImages[index].categoryImageId,
                                onClick = {
                                    listener.onClickCategory(
                                        state.categoryImages[index].categoryImageId
                                    )
                                }
                            )
                        }
                    }

                }
                HoneyFilledIconButton(
                    label = "Add",
                    onClick ={
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
    }
    Loading(state = state.isLoading)
}

@Preview
@Composable
fun HoneyMartTitle(modifier: Modifier =Modifier){
    Row(modifier = modifier.padding(
        top = MaterialTheme.dimens.space56,
        start = MaterialTheme.dimens.space32,
        bottom = MaterialTheme.dimens.space24
    )) {
        Icon(
            modifier = Modifier
                .size(MaterialTheme.dimens.icon32)
                .padding(end = MaterialTheme.dimens.space2),
            painter = painterResource(id = R.drawable.icon_cart),
            contentDescription = "",
            tint = primary100
        )
        Text(
            text = "Honey",
            style = Typography.displayMedium.copy(color = primary100),
        )
        Text(
            text = "Mart",
            style = Typography.displayMedium.copy(color = blackOn87),
        )
    }
}