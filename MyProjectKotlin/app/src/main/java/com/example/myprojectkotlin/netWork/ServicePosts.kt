package com.example.aplicationkotlin01.netWork

import com.example.myprojectkotlin.netWork.UserResposeUsers
import retrofit2.Response
import retrofit2.http.GET

interface ServicePosts {
    @GET("posts")
    suspend fun getPosts() : Response<List<UserResponsePosts>>

    @GET("users")
    suspend fun getUsers() : Response<List<UserResposeUsers>>
}