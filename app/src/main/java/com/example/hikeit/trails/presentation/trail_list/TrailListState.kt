package com.example.hikeit.trails.presentation.trail_list

import androidx.compose.runtime.Immutable
import com.example.hikeit.trails.presentation.models.TrailUi

@Immutable
data class TrailListState(
    val isLoading: Boolean = false,
    val trails: List<TrailUi> = emptyList(),
)
