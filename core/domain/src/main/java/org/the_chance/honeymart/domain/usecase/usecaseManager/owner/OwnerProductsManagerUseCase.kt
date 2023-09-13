package org.the_chance.honeymart.domain.usecase.usecaseManager.owner

import org.the_chance.honeymart.domain.usecase.AddProductImagesUseCase
import org.the_chance.honeymart.domain.usecase.AddProductUseCase
import org.the_chance.honeymart.domain.usecase.DeleteProductByIdUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetProductDetailsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateImageProductUseCase
import org.the_chance.honeymart.domain.usecase.UpdateProductDetailsUseCase
import javax.inject.Inject

data class OwnerProductsManagerUseCase @Inject constructor(
    val updateProductImagesUseCase: UpdateImageProductUseCase,
    val updateProductDetailsUseCase: UpdateProductDetailsUseCase,
    val deleteProductByIdUseCase: DeleteProductByIdUseCase,
    val getProductDetailsUseCase: GetProductDetailsUseCase,
    val addProductImagesUseCase: AddProductImagesUseCase,
    val addProductUseCase: AddProductUseCase,
    val getAllProducts: GetAllProductsByCategoryUseCase,
)
