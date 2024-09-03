package com.buytown.ru.data.model

data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val price: Double
)
