package com.buytown.ru.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "http://147.45.153.157:8000/"

    // Создаем OkHttpClient с таймаутами
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // Таймаут на подключение
            .readTimeout(10, TimeUnit.SECONDS) // Таймаут на чтение
            .writeTimeout(10, TimeUnit.SECONDS) // Таймаут на запись
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
