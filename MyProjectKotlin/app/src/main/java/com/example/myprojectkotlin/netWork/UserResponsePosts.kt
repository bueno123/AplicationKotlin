package com.example.aplicationkotlin01.netWork

data class UserResponsePosts (
    val id: Int,
    val user_id: String,
    val username: String,
    val user_image: String,
    val body: String,
    val image: String,
    val likes: String,
    val comment: Any
)