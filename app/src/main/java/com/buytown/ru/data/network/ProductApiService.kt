package com.buytown.ru.data.network

import com.buytown.ru.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Header

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(@Header("Authorization") token: String): List<Product>
}
