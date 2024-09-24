package com.buytown.ru.data.network

import android.content.Context
import com.buytown.ru.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()

        val token = TokenManager.getToken(context)
        if (token != null) {
            newRequestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(newRequestBuilder.build())
    }
}
