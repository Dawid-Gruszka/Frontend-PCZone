package com.david.pczone.Models

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val quantity: Int,
    val price: Int,
    val category: Category,
    val image_url: String?
)
