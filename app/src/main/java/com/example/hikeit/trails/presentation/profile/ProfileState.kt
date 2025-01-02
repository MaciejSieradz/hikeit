package com.example.hikeit.trails.presentation.profile

import androidx.compose.runtime.Immutable
import com.example.hikeit.trails.presentation.models.TrailUi

@Immutable
data class ProfileState(
    val isLoading: Boolean = false,
    val trails: List<TrailUi> = emptyList()
)
