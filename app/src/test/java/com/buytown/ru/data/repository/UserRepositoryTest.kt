package com.buytown.ru.data.repository

import com.buytown.ru.data.model.User
import com.buytown.ru.data.network.ApiService
import com.buytown.ru.data.network.LoginResponse
import com.buytown.ru.data.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.MockitoAnnotations

class UserRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepository(apiService)
    }

    @Test
    fun `register user success`() = runBlocking {
        val user = User("username", "email@example.com", "password")

        userRepository.register(user)

        verify(apiService).register(user)
    }

    @Test(expected = Exception::class)
    fun `register user failure`() = runBlocking {
        val user = User("username", "email@example.com", "password")
        whenever(apiService.register(user)).thenThrow(RuntimeException("Failed to register"))

        userRepository.register(user)
    }

    @Test
    fun `login user success`() = runBlocking {
        val email = "email@example.com"
        val password = "password"
        val token = "token123"
        whenever(apiService.login(mapOf("email" to email, "password" to password))).thenReturn(
            LoginResponse(token)
        )

        val result = userRepository.login(email, password)

        assertEquals(token, result)
    }

    @Test(expected = Exception::class)
    fun `login user failure`(): Unit = runBlocking {
        val email = "email@example.com"
        val password = "password"
        whenever(apiService.login(mapOf("email" to email, "password" to password))).thenThrow(RuntimeException("Failed to login"))

        userRepository.login(email, password)
    }
}