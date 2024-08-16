package com.practice.openinapp.networking

import com.practice.openinapp.model_class.TopLinks
import com.practice.openinapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiServices {
    @GET("v1/dashboardNew")
    @Headers("Authorization: Bearer ${Constants.HEADERS}")
    suspend fun getLinks(): Response<TopLinks>
}