package com.example.hikeit.trails.presentation.models

import android.icu.text.NumberFormat
import com.example.hikeit.trails.domain.TrailDetails
import java.util.Locale

data class TrailDetailUi(
    val trailPhotoUrl: String,
    val title : String,
    val difficulty: String,
    val rating: String,
    val distance: String,
    val elevationGain: String,
    val maxHeight: String,
    val estimatedHikingTime: String,
    val description: String
)

fun TrailDetails.toTrailDetailsUi() : TrailDetailUi {

    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 1
        minimumFractionDigits = 1
    }

    return TrailDetailUi(
        trailPhotoUrl = trailPhotoUrl,
        title = title,
        difficulty = difficulty,
        rating = "${formatter.format(rating)} ($numberOfRatings)",
        distance = "${formatter.format(distance)} km",
        elevationGain = "$elevationGain m",
        maxHeight = "$maxHeight m",
        estimatedHikingTime = "${estimatedHikingTime.hours} km ${estimatedHikingTime.minutes} min",
        description = description
    )
}