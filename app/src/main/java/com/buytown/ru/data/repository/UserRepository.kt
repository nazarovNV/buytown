package com.buytown.ru.data.repository

import com.buytown.ru.data.network.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun register(user: com.buytown.ru.data.network.User) = try {
        apiService.register(user)
    } catch (e: Exception) {
        throw Exception("Не удалось зарегистрироваться. Повторите попытку.")
    }

    suspend fun login(email: String, password: String): String {
        return try {
            val response = apiService.login(mapOf("email" to email, "password" to password))
            response.access_token
        } catch (e: Exception) {
            throw Exception("Не удалось выполнить вход. Проверьте свои учетные данные.")
        }
    }
}
