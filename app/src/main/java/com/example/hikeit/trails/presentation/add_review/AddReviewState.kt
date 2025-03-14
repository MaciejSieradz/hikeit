package com.example.hikeit.trails.presentation.add_review

import android.net.Uri
import com.example.hikeit.trails.domain.Review

data class AddReviewState(
    val rating: Int,
    val photosUri: List<Uri> = emptyList(),
    val photos: List<ByteArray> = emptyList(),
    val description: String? = null
)

fun AddReviewState.createReview() : Review {
    return Review(
        mark = rating,
        description = description,
        photos = photos
    )
}