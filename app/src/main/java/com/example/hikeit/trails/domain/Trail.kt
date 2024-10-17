package com.example.hikeit.trails.domain

data class Trail(
    val id: String,
    val photoUrl: String,
    val title: String,
    val mountains: String,
    val rating: Double,
    val difficulty: String,
    val distance: Double,
    val estimatedHikingTime: EstimatedHikingTime
)

data class EstimatedHikingTime(
    val hours: Int,
    val minutes: Int
)