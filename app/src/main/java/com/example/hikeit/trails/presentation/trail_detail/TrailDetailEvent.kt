package com.example.hikeit.trails.presentation.trail_detail

import com.example.hikeit.core.domain.util.NetworkError

interface TrailDetailEvent {

    data class Error(val error: NetworkError) : TrailDetailEvent
    data object MarkedTrail : TrailDetailEvent
    data object UnmarkedTrail : TrailDetailEvent
}