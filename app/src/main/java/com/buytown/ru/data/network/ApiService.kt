package com.buytown.ru.data.network

import retrofit2.http.Body
import retrofit2.http.POST

data class User(val username: String, val email: String, val password: String)

interface ApiService {
    @POST("register")
    suspend fun register(@Body user: User)

    @POST("login")
    suspend fun login(@Body credentials: Map<String, String>): LoginResponse
}
