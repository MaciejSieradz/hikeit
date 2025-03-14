package com.example.hikeit.trails.domain

data class Trail(
    val id: String,
    val photoUrl: String,
    val title: String,
    val rating: Double,
    val difficulty: String,
    val distance: Double,
    val estimatedHikingTime: EstimatedHikingTime
)
