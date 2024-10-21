package com.example.hikeit.trails.domain

data class Comment(
    val userAvatarUrl: String,
    val username: String,
    val rating: Int,
    val commentDate: String,
    val comment: String
)
