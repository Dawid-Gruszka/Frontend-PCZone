package com.david.pczone.Models

data class Order(
    val id: Int,
    val user_id: Int,
    val total_price: Double,
    val created_at: String,
    val items: List<OrderItem> = emptyList()
)

data class OrderCreateResponse(
    val message: String,
    val order_id: Int
)

data class OrderItem(
    val id: Int,
    val product_id: Int,
    val quantity: Int
)
