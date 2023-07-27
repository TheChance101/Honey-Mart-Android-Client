package org.the_chance.honeymart.ui.feature.orders

data class OrderItemState(
    val orderId: Long = 0L,
    val marketName: String = "",
    val quantity: Int = 0,
    val orderPrice: Double = 0.0,
    val imageUrl: String = ""
)

object FakeList {
    val fakeData = listOf(
        OrderItemState(
            orderId = 12345,
            marketName = "Sample Order 1",
            quantity = 12,
            orderPrice = 100.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 1",
            quantity = 12,
            orderPrice = 100.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        ),
        OrderItemState(
            orderId = 67890,
            marketName = "Sample Order 2",
            quantity = 2,
            orderPrice = 200.0,
            imageUrl = "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
        )
    )
}