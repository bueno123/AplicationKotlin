package com.example.myprojectkotlin.netWork

import com.example.aplicationkotlin01.netWork.Social

data class UserResposeUsers (
    val id: String,
    val username: String,
    val name: String,
    val lastname: String,
    val image: String,
    val occupation: String,
    val age: String,
    val email: String,
    val location:String,
    val phone:String,
    val social: Social
)