package com.buytown.ru.data.repository

import com.buytown.ru.data.network.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getProducts() = apiService.getProducts()
}
