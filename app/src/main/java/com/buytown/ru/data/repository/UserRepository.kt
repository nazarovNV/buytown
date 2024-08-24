package com.buytown.ru.data.repository

import com.buytown.ru.data.network.ApiClient
import com.buytown.ru.data.network.ApiService
import com.buytown.ru.data.network.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun register(user: User) = apiService.register(user)
    suspend fun login(email: String, password: String) = apiService.login(mapOf("email" to email, "password" to password))
}
