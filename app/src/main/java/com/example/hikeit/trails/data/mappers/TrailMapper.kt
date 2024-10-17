package com.example.hikeit.trails.data.mappers

import com.example.hikeit.trails.data.networking.dto.TrailDto
import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.Trail

fun TrailDto.toTrail(): Trail {
    return Trail(
        id = id,
        photoUrl = photoUrl,
        title = title,
        mountains = mountains,
        rating = rating,
        difficulty = difficulty,
        distance = distance,
        estimatedHikingTime = EstimatedHikingTime(
            estimatedHikingTime.hours,
            estimatedHikingTime.minutes
        )
    )
}