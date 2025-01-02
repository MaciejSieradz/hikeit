package com.example.hikeit.trails.domain

data class Review(
    val mark: Int,
    val description: String?,
    val photos: List<ByteArray> = emptyList(),
)
