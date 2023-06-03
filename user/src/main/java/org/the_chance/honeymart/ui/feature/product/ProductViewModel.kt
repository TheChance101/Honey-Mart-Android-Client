package org.the_chance.honeymart.ui.feature.product

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.ui.product.CategoryProductInteractionListener
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(

) : BaseViewModel(), ProductInteractionListener,
    CategoryProductInteractionListener {
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