package com.practice.openinapp.networking

import com.practice.openinapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {

    private fun getInstance(): Retrofit{
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(Interceptor {chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(
                    "x-api-key",
                    Constants.HEADERS
                ).build()
            chain.proceed(request)
        })
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    val apiService: ApiServices = getInstance().create(
        ApiServices::class.java
    )
}