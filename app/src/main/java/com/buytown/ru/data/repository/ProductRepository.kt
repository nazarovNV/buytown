package com.buytown.ru.data.repository

import com.buytown.ru.data.model.Product
import com.buytown.ru.data.network.ProductApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ProductApiService
) {
    suspend fun getProducts(token: String): List<Product> = apiService.getProducts(token)
}
