package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class ValidateOwnerFieldsUseCase @Inject constructor() {
    fun validateCategoryNameField(categoryName: String): ValidationState {
        return when {
            categoryName.isBlank() -> ValidationState.BLANK_CATEGORY_NAME
            categoryName.length < 4 -> ValidationState.SHORT_CATEGORY_NAME
            categoryName.length > 16 -> ValidationState.LONG_CATEGORY_NAME
            !categoryName.matches(Regex("^[a-zA-Z]{4,16}$")) -> {
                ValidationState.INVALID_CATEGORY_NAME
            }

            else -> ValidationState.VALID_CATEGORY_NAME
        }
    }

    fun validateProductNameField(productName: String): ValidationState {
        return when {
            productName.isBlank() -> ValidationState.BLANK_PRODUCT_NAME
            productName.length < 4 -> ValidationState.SHORT_PRODUCT_NAME
            productName.length > 20 -> ValidationState.LONG_PRODUCT_NAME
            !productName.matches(Regex("^[A-Za-z0-9\\s\\[\\]\\(\\)\\-.,&]{4,20}$")) -> {
                ValidationState.INVALID_PRODUCT_NAME
            }

            else -> ValidationState.VALID_PRODUCT_NAME
        }
    }

    fun validateProductPrice(productPrice: String): ValidationState {
        return when {
            productPrice.isBlank() -> ValidationState.BLANK_PRODUCT_PRICE
            !productPrice.matches(Regex("^[0-9]{1,6}(\\.[0-9]{1,2})?$")) -> {
                ValidationState.INVALID_PRODUCT_PRICE
            }

            else -> ValidationState.VALID_PRODUCT_PRICE
        }
    }

    fun validateProductDescription(productDescription: String): ValidationState {
        return when {
            productDescription.isBlank() -> ValidationState.BLANK_PRODUCT_DESCRIPTION
            productDescription.length < 6 -> ValidationState.SHORT_PRODUCT_DESCRIPTION
            productDescription.length > 500 -> ValidationState.LONG_PRODUCT_DESCRIPTION
            else -> ValidationState.VALID_PRODUCT_DESCRIPTION
        }
    }

    fun validateCouponsDiscountPercentage(discountPercentage: String): ValidationState {
        return when {
            discountPercentage.isBlank() -> ValidationState.BLANK_COUPON_DISCOUNT_PERCENTAGE
            !discountPercentage.matches(Regex("^(100(\\.0{1,2})?|\\d{1,2}(\\.\\d{1,2})?)$")) -> {
                ValidationState.INVALID_COUPON_DISCOUNT_PERCENTAGE
            }

            else -> ValidationState.VALID_COUPON_DISCOUNT_PERCENTAGE
        }
    }

    fun validateCouponCount(couponCount: String): ValidationState {
        return when {
            couponCount.isBlank() -> ValidationState.BLANK_COUPON_COUNT
            couponCount.length > 9 -> ValidationState.LONG_COUPON_COUNT
            !couponCount.matches(Regex("^[1-9]\\d*$")) -> {
                ValidationState.INVALID_COUPON_COUNT
            }

            else -> ValidationState.VALID_COUPON_COUNT
        }
    }
}

