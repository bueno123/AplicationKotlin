package com.example.myprojectkotlin.netWork

import com.example.aplicationkotlin01.netWork.ServicePosts
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RepositoryUsers {
    object RetrofitRepository {
        const val BASE_URL = "https://my-json-server.typicode.com/rzkbrian/public_db/"

        fun getServiceUsers () : ServicePosts {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(ServicePosts::class.java)
        }
    }
}