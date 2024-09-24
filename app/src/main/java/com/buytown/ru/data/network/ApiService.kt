package com.buytown.ru.data.network

import com.buytown.ru.data.model.Product
import com.buytown.ru.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(@Body user: User)

    @POST("login")
    suspend fun login(@Body credentials: Map<String, String>): LoginResponse

    @GET("products")
    suspend fun getProducts(): List<Product>

    @POST("products")
    suspend fun addProduct(@Body product: Product): AddProductResponse
}

data class AddProductResponse(val message: String)
