package com.example.hikeit.trails.presentation.models

import com.example.hikeit.trails.domain.Comment

data class UserCommentUi(
    val userAvatarUrl: String,
    val username: String,
    val rating: Int,
    val commentDate: String,
    val comment: String?,
    val photosUrl: List<String>
)

fun Comment.toUserCommentUi() : UserCommentUi {
    return UserCommentUi(
        userAvatarUrl = userAvatarUrl,
        username = username,
        rating = rating,
        commentDate = commentDate,
        comment = comment,
        photosUrl = photosUrl
    )
}
