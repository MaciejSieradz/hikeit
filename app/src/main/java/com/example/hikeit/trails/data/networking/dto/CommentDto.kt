package com.example.hikeit.trails.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val userAvatarUrl: String,
    val username: String,
    val rating: Int,
    val commentDate: String,
    val comment: String
)