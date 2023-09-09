package org.the_chance.honeymart.domain.util

//region User
open class UserException : Exception()
class InvalidInputException : UserException()
class UsernameAlreadyExistException : UserException()
class InvalidUserIdException : UserException()
class InvalidUserNameOrPasswordException : UserException()
class InvalidEmailException : UserException()
class InvalidNameException : UserException()
class EmailAlreadyExistException : UserException()
class InvalidUserNameInputException : UserException()
class InvalidPasswordInputException : UserException()
class UnKnownUserException : UserException()
//endregion

//region Owner
open class OwnerException : Exception()
class InvalidOwnerIdException : OwnerException()
//endregion

//region Admin
open class AdminException : Exception()
class AdminAccessDeniedException : AdminException()
//endregion

//region Market
open class MarketException : Exception()
class InvalidMarketIdException : MarketException()
class MarketNotApprovedException : MarketException()
class MarketAlreadyExistException : MarketException()
class InvalidMarketNameException : MarketException()
class InvalidMarketDescriptionException : MarketException()
class MarketDeletedException : MarketException()
//endregion

//region Category
open class CategoryException : Exception()
class InvalidCategoryIdException : CategoryException()
class InvalidCategoryNameException : CategoryException()
class CategoryDeletedException : CategoryException()
class CategoryNameNotUniqueException : CategoryException()
class NotValidCategoryListException : CategoryException()
//endregion

//region Product
open class ProductException : Exception()
class InvalidProductIdException : ProductException()
class InvalidProductNameException : ProductException()
class InvalidProductDescriptionException : ProductException()
class InvalidProductPriceException : ProductException()
class ProductDeletedException : ProductException()
class ProductAlreadyInWishListException : ProductException()
class ProductNotInSameCartMarketException : ProductException()
//endregion

//region Order
open class OrderException : Exception()
class InvalidOrderIdException : OrderException()
class InvalidStateOrderException : OrderException()
class CantUpdateOrderStateException : OrderException()
//endregion

//region Cart
open class CartException : Exception()
class EmptyCartException : CartException()
class CountInvalidInputException : CartException()
class InvalidPercentageException : CartException()
//endregion

//region Image
open class ImageException : Exception()
class InvalidImageIdException : ImageException()
class ImageNotFoundException : ImageException()
class AddImageFailedException : ImageException()
//endregion

//region Coupon
open class CouponException : Exception()
class InvalidCouponIdException : CouponException()
class InvalidCouponException : CouponException()
class CouponAlreadyClippedException : CouponException()
class InvalidExpirationDateException : CouponException()
class InvalidCountException : CouponException()
//endregion

//region GeneralException
open class GeneralException : Exception()
class IdNotFoundException : GeneralException()
class InvalidPageNumberException : GeneralException()
class MissingQueryParameterException : GeneralException()
//endregion

//region Token
open class TokenException : Exception()
class UnauthorizedException : TokenException()
class InvalidRuleException : TokenException()
class TokenExpiredException : TokenException()
class InvalidTokenException : TokenException()
class InvalidTokenTypeException : TokenException()
class InvalidDeviceTokenException : TokenException()
class InvalidApiKeyException : TokenException()
//endregion

//region Network
open class NetworkException : Exception()
class NoConnectionException : NetworkException()
class InvalidDataException : NetworkException()
class NotFoundException : NetworkException()
class InternalServerException : NetworkException()
class ForbiddenException : NetworkException()
class UnAuthorizedException : NetworkException()
//endregion


sealed interface ErrorHandler {

    //region User
    object InvalidInput : ErrorHandler
    object UsernameAlreadyExist : ErrorHandler
    object InvalidUserId : ErrorHandler
    object InvalidUserNameOrPassword : ErrorHandler
    object InvalidEmail : ErrorHandler
    object InvalidName : ErrorHandler
    object EmailAlreadyExist : ErrorHandler
    object InvalidUserNameInput : ErrorHandler
    object InvalidPasswordInput : ErrorHandler
    object UnKnownUser : ErrorHandler
    //endregion

    //region Owner
    object InvalidOwnerId : ErrorHandler
    //endregion

    //region Admin
    object AdminAccessDenied : ErrorHandler
    //endregion

    //region Market
    object InvalidMarketId : ErrorHandler
    object MarketNotApproved : ErrorHandler
    object MarketAlreadyExist : ErrorHandler
    object InvalidMarketName : ErrorHandler
    object InvalidMarketDescription : ErrorHandler
    object MarketDeleted : ErrorHandler

    //endregion

    //region Category
    object InvalidCategoryId : ErrorHandler
    object InvalidCategoryName : ErrorHandler
    object CategoryDeleted : ErrorHandler
    object CategoryNameNotUnique : ErrorHandler
    object NotValidCategoryList : ErrorHandler
    //endregion

    //region Product
    object InvalidProductId : ErrorHandler
    object InvalidProductName : ErrorHandler
    object InvalidProductDescription : ErrorHandler
    object InvalidProductPrice : ErrorHandler
    object ProductDeleted : ErrorHandler
    object ProductAlreadyInWishList : ErrorHandler
    object ProductNotInSameCartMarket : ErrorHandler

    //endregion

    //region Order
    object InvalidOrderId : ErrorHandler
    object InvalidStateOrder : ErrorHandler
    object CantUpdateOrderState : ErrorHandler
    //endregion

    //region Cart
    object EmptyCart : ErrorHandler
    object CountInvalidInput : ErrorHandler
    object InvalidPercentage : ErrorHandler
    //endregion

    //region Image
    object InvalidImageId : ErrorHandler
    object ImageNotFound : ErrorHandler
    object AddImageFailed : ErrorHandler
    //endregion

    //region Coupon
    object InvalidCouponId : ErrorHandler
    object InvalidCoupon : ErrorHandler
    object CouponAlreadyClipped : ErrorHandler
    object InvalidExpirationDate : ErrorHandler
    object InvalidCount : ErrorHandler
    //endregion

    //region GeneralException
    object IdNotFound : ErrorHandler
    object InvalidPageNumber : ErrorHandler
    object MissingQueryParameter : ErrorHandler
    //endregion

    //region Token
    object UnAuthorized : ErrorHandler
    object InvalidRule : ErrorHandler
    object TokenExpired : ErrorHandler
    object InvalidToken : ErrorHandler
    object InvalidTokenType : ErrorHandler
    object InvalidDeviceToken : ErrorHandler
    object InvalidApiKey : ErrorHandler
    //endregion

    //region Network
    object NoConnection : ErrorHandler
    object InvalidData : ErrorHandler
    object NotFound : ErrorHandler
    object InternalServer : ErrorHandler
    object Forbidden : ErrorHandler
    //endregion
}

fun handelUserException(
    exception: UserException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidInputException -> onError(ErrorHandler.InvalidInput)
        is UsernameAlreadyExistException -> onError(ErrorHandler.UsernameAlreadyExist)
        is InvalidUserIdException -> onError(ErrorHandler.InvalidUserId)
        is InvalidUserNameOrPasswordException -> onError(ErrorHandler.InvalidUserNameOrPassword)
        is InvalidEmailException -> onError(ErrorHandler.InvalidEmail)
        is InvalidNameException -> onError(ErrorHandler.InvalidName)
        is EmailAlreadyExistException -> onError(ErrorHandler.EmailAlreadyExist)
        is InvalidUserNameInputException -> onError(ErrorHandler.InvalidUserNameInput)
        is InvalidPasswordInputException -> onError(ErrorHandler.InvalidPasswordInput)
        is UnKnownUserException -> onError(ErrorHandler.UnKnownUser)
    }
}

fun handelOwnerException(
    exception: OwnerException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidOwnerIdException -> onError(ErrorHandler.InvalidOwnerId)
    }
}

fun handelAdminException(
    exception: AdminException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is AdminAccessDeniedException -> onError(ErrorHandler.AdminAccessDenied)
    }
}

fun handelMarketException(
    exception: MarketException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidMarketIdException -> onError(ErrorHandler.InvalidMarketId)
        is MarketNotApprovedException -> onError(ErrorHandler.MarketNotApproved)
        is MarketAlreadyExistException -> onError(ErrorHandler.MarketAlreadyExist)
        is InvalidMarketNameException -> onError(ErrorHandler.InvalidMarketName)
        is InvalidMarketDescriptionException -> onError(ErrorHandler.InvalidMarketDescription)
        is MarketDeletedException -> onError(ErrorHandler.MarketDeleted)
    }
}

fun handelCategoryException(
    exception: CategoryException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidCategoryIdException -> onError(ErrorHandler.InvalidCategoryId)
        is InvalidCategoryNameException -> onError(ErrorHandler.InvalidCategoryName)
        is CategoryDeletedException -> onError(ErrorHandler.CategoryDeleted)
        is CategoryNameNotUniqueException -> onError(ErrorHandler.CategoryNameNotUnique)
        is NotValidCategoryListException -> onError(ErrorHandler.NotValidCategoryList)
    }
}

fun handelProductException(
    exception: ProductException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidProductIdException -> onError(ErrorHandler.InvalidProductId)
        is InvalidProductNameException -> onError(ErrorHandler.InvalidProductName)
        is InvalidProductDescriptionException -> onError(ErrorHandler.InvalidProductDescription)
        is InvalidProductPriceException -> onError(ErrorHandler.InvalidProductPrice)
        is ProductDeletedException -> onError(ErrorHandler.ProductDeleted)
        is ProductAlreadyInWishListException -> onError(ErrorHandler.ProductAlreadyInWishList)
        is ProductNotInSameCartMarketException -> onError(ErrorHandler.ProductNotInSameCartMarket)
    }
}

fun handelOrderException(
    exception: OrderException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidOrderIdException -> onError(ErrorHandler.InvalidOrderId)
        is InvalidStateOrderException -> onError(ErrorHandler.InvalidStateOrder)
        is CantUpdateOrderStateException -> onError(ErrorHandler.CantUpdateOrderState)
    }
}

fun handelCartException(
    exception: CartException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is EmptyCartException -> onError(ErrorHandler.EmptyCart)
        is CountInvalidInputException -> onError(ErrorHandler.CountInvalidInput)
        is InvalidPercentageException -> onError(ErrorHandler.InvalidPercentage)
    }
}

fun handelImageException(
    exception: ImageException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidImageIdException -> onError(ErrorHandler.InvalidImageId)
        is ImageNotFoundException -> onError(ErrorHandler.ImageNotFound)
        is AddImageFailedException -> onError(ErrorHandler.AddImageFailed)
    }
}

fun handelCouponException(
    exception: CouponException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is InvalidCouponIdException -> onError(ErrorHandler.InvalidCouponId)
        is InvalidCouponException -> onError(ErrorHandler.InvalidCoupon)
        is CouponAlreadyClippedException -> onError(ErrorHandler.CouponAlreadyClipped)
        is InvalidExpirationDateException -> onError(ErrorHandler.InvalidExpirationDate)
        is InvalidCountException -> onError(ErrorHandler.InvalidCount)
    }
}

fun handelGeneralException(
    exception: GeneralException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is IdNotFoundException -> onError(ErrorHandler.IdNotFound)
        is InvalidPageNumberException -> onError(ErrorHandler.InvalidPageNumber)
        is MissingQueryParameterException -> onError(ErrorHandler.MissingQueryParameter)
    }
}

fun handelTokenException(
    exception: TokenException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is UnauthorizedException -> onError(ErrorHandler.UnAuthorized)
        is InvalidRuleException -> onError(ErrorHandler.InvalidRule)
        is TokenExpiredException -> onError(ErrorHandler.TokenExpired)
        is InvalidTokenException -> onError(ErrorHandler.InvalidToken)
        is InvalidTokenTypeException -> onError(ErrorHandler.InvalidTokenType)
        is InvalidDeviceTokenException -> onError(ErrorHandler.InvalidDeviceToken)
        is InvalidApiKeyException -> onError(ErrorHandler.InvalidApiKey)
    }
}

fun handelNetworkException(
    exception: NetworkException,
    onError: (t: ErrorHandler) -> Unit,
) {
    when (exception) {
        is NoConnectionException -> onError(ErrorHandler.NoConnection)
        is InvalidDataException -> onError(ErrorHandler.InvalidData)
        is NotFoundException -> onError(ErrorHandler.NotFound)
        is InternalServerException -> onError(ErrorHandler.InternalServer)
        is ForbiddenException -> onError(ErrorHandler.Forbidden)
        is UnAuthorizedException -> onError(ErrorHandler.UnAuthorized)
    }
}
