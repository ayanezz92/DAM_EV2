package com.example.vidasalud2.model

data class Post(
    val id: Int,
    val userName: String,
    val content: String,
    val likes: Int,
    val timeAgo: String
)