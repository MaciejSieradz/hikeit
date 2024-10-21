package com.example.hikeit.trails.presentation.trail_detail

import androidx.compose.runtime.Immutable
import com.example.hikeit.trails.presentation.models.TrailDetailUi
import com.example.hikeit.trails.presentation.models.UserCommentUi

@Immutable
data class TrailDetailState(
    val isLoading: Boolean = false,
    val trailDetails: TrailDetailUi? = null,
    val comments: List<UserCommentUi> = emptyList()
)
