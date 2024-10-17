package com.example.hikeit.trails.presentation.trail_detail

import androidx.compose.runtime.Immutable
import com.example.hikeit.data.model.TrailDetails

@Immutable
data class TrailDetailState(
    val isLoading: Boolean = false,
    val trailDetails: TrailDetails? = null
)
