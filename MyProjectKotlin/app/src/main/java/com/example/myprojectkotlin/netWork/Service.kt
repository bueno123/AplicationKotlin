package com.example.aplicationkotlin01.netWork

import retrofit2.Response
import retrofit2.http.GET


interface Service {
    @GET("profile")
    suspend fun getProfile() : Response<UserResponse>
}