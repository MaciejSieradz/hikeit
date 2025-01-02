package com.example.hikeit.trails.domain

import androidx.compose.runtime.Immutable
import com.example.hikeit.core.data.xml.Route

@Immutable
data class TrailDetails (
    val id: String,
    val trailPhotoUrl: String,
    val title : String,
    val difficulty: String,
    val rating: Double,
    val numberOfRatings: Int,
    val distance: Double,
    val elevationGain: Int,
    val maxHeight: Int,
    val estimatedHikingTime: EstimatedHikingTime,
    val description: String,
    val route: Route,
    val comments: List<Comment>,
    val isMarked: Boolean
)