package com.buytown.ru.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.Response

// Интерceptor для обработки ретраев
class RetryInterceptor(private val maxRetry: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response
        var tryCount = 0

        val request = chain.request()
        do {
            response = chain.proceed(request)
            tryCount++
        } while (!response.isSuccessful && tryCount < maxRetry)

        return response
    }
}

object ApiClient {
    private const val BASE_URL = "http://147.45.153.157:8000/"
    private const val TIMEOUT_DURATION = 10L

    // Создаем OkHttpClient с таймаутами и механизмом повторной попытки
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS) // Таймаут на подключение
            .readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS) // Таймаут на чтение
            .writeTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS) // Таймаут на запись
            .addInterceptor(RetryInterceptor(3)) // Установите максимум ретраев
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Устанавливаем кастомный OkHttpClient с таймаутами
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
