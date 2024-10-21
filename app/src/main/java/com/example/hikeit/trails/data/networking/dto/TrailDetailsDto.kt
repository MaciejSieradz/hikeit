package com.example.hikeit.trails.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class TrailDetailsDto(
    val id: String,
    val trailPhotoUrl: String,
    val title : String,
    val difficulty: String,
    val rating: Double,
    val numberOfRatings: Int,
    val distance: Double,
    val elevationGain: Int,
    val maxHeight: Int,
    val estimatedHikingTime: EstimatedHikingTimeDto,
    val description: String,
    val comments: List<CommentDto>
)
