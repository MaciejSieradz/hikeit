package com.example.hikeit.trails.presentation.models

import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.Trail

data class TrailUi(
    val id: String,
    val photoUrl: String,
    val title: String,
    val mountains: String,
    val trailAdditionalInfo: String
)

fun Trail.toTrailUi(): TrailUi {

    fun createAdditionalInfo(
        rating: Double,
        difficulty: String,
        estimatedHikingTime: EstimatedHikingTime
    ) = "$rating - $difficulty - Szac. ${estimatedHikingTime.hours} godz. ${estimatedHikingTime.minutes} min."

    return TrailUi(
        id = id,
        photoUrl = photoUrl,
        title = title,
        mountains = mountains,
        trailAdditionalInfo = createAdditionalInfo(rating, difficulty, estimatedHikingTime)
    )
}