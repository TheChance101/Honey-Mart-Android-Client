package org.the_chance.ui.product

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.the_chance.domain.usecase.GetAllMarketUseCase
import org.the_chance.domain.usecase.GetAllProductsUseCase
import org.the_chance.ui.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllMarketUseCase
) : BaseViewModel() , CategoryProductInteractionListener , ProductInteractionListener {
    override val TAG: String = this::class.java.simpleName



    init {
        viewModelScope.launch {
            try {
            val result = getAllProductsUseCase
            log("products : ${result.invoke()}")
            }catch (e:Exception){
                log("products : ${e.message}")
            }
        }
    }


    override fun onClickCategoryProduct(id: Int) {
    }

    override fun onClickProduct(id: Int) {

    }

}