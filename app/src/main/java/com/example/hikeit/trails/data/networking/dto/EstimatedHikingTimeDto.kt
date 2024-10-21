package com.example.hikeit.trails.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class EstimatedHikingTimeDto(
    val hours: Int,
    val minutes: Int
)