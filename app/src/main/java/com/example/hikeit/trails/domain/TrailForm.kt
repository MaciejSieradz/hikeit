package com.example.hikeit.trails.domain

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class TrailForm(
    val title: String,
    val difficulty: Difficulty,
    val description: String,
    val estimatedHikingTime: EstimatedHikingTime,
    val gpxUri: Uri,
    val photosUri: List<Uri> = emptyList()
)
