package org.the_chance.domain.usecase

import org.the_chance.data.repository.HoneyMartRepository
import org.the_chance.domain.model.Product
import org.the_chance.domain.model.asProduct
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(): List<Product>? {
        return honeyMartRepository.getAllProducts()?.map { it.asProduct() }
    }
}