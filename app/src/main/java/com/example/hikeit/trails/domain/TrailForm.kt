package com.example.hikeit.trails.domain

import androidx.compose.runtime.Immutable

@Immutable
data class TrailForm(
    val title: String,
    val difficulty: Difficulty,
    val description: String,
    val estimatedHikingTime: EstimatedHikingTime,
    val gpx: ByteArray,
    val elevation: Int,
    val distance: Double,
    val maxElevation: Int,
    val negativeElevation: Int,
    val photos: List<ByteArray> = emptyList(),
)
