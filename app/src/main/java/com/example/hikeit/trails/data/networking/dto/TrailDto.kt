package com.example.hikeit.trails.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class TrailDto(
    val id: String,
    val photoUrl: String,
    val title: String,
    val rating: Double,
    val difficulty: String,
    val distance: Double,
    val estimatedHikingTime: EstimatedHikingTimeDto
)

