package org.the_chance.honeymart.domain.usecase

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
