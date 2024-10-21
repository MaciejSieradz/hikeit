package com.example.hikeit.trails.presentation.models

data class UserCommentUi(
    val userAvatarUrl: String,
    val username: String,
    val rating: Int,
    val commentDate: String,
    val comment: String
)
