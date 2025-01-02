package com.example.hikeit.trails.presentation.models

import com.example.hikeit.core.data.xml.Route
import com.example.hikeit.trails.data.mappers.getPolishNumberFormatter
import com.example.hikeit.trails.domain.TrailDetails

data class TrailDetailUi(
    val trailId: String,
    val trailPhotos: List<String>,
    val title : String,
    val difficulty: String,
    val rating: String,
    val distance: String,
    val elevationGain: String,
    val maxHeight: String,
    val estimatedHikingTime: String,
    val description: String,
    val route: Route,
    val isMarked: Boolean
)

fun TrailDetails.toTrailDetailsUi() : TrailDetailUi {

    val formatter = getPolishNumberFormatter()

    return TrailDetailUi(
        trailId = id,
        trailPhotos = trailPhotos,
        title = title,
        difficulty = difficulty,
        rating = "${formatter.format(rating)} ($numberOfRatings)",
        distance = "${formatter.format(distance)} km",
        elevationGain = "$elevationGain m",
        maxHeight = "$maxHeight m",
        estimatedHikingTime = "${estimatedHikingTime.hours} godz. ${estimatedHikingTime.minutes} min.",
        description = description,
        route = route,
        isMarked = isMarked
    )
}