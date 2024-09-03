package com.buytown.ru.data.network

import com.buytown.ru.data.model.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class User(val username: String, val email: String, val password: String)

interface ApiService {
    @POST("register")
    suspend fun register(@Body user: User)

    @POST("login")
    suspend fun login(@Body credentials: Map<String, String>): LoginResponse

    @GET("products")
    suspend fun getProducts(): List<Product>
}