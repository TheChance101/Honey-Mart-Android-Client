package org.the_chance.honeymart.domain.usecase.usecaseManager.user

import org.the_chance.honeymart.domain.usecase.GetAllProductReviewsUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsUseCase
import org.the_chance.honeymart.domain.usecase.GetProductDetailsUseCase
//import org.the_chance.honeymart.domain.usecase.GetProductRatingUseCase
import javax.inject.Inject

data class UserProductManagerUseCase @Inject constructor(
    val getAllProductsByCategoryUseCase: GetAllProductsByCategoryUseCase,
    val getAllProductsUseCase: GetAllProductsUseCase,
    val getProductDetailsUseCase: GetProductDetailsUseCase,
//    val getProductRatingUseCase: GetProductRatingUseCase,
    val getAllProductReviewsUseCase: GetAllProductReviewsUseCase,
)
