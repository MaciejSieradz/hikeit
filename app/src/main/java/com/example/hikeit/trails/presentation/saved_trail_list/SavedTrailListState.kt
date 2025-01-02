package com.example.hikeit.trails.presentation.saved_trail_list

import androidx.compose.runtime.Immutable
import com.example.hikeit.trails.presentation.models.TrailUi

@Immutable
data class SavedTrailListState(
    val isLoading: Boolean = false,
    val trails: List<TrailUi> = emptyList()
)