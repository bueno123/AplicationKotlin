package com.example.aplicationkotlin01.netWork

import retrofit2.Response
import retrofit2.http.GET

interface ServicePosts {
    @GET("posts")
    suspend fun getPosts() : Response<List<UserResponsePosts>>
}