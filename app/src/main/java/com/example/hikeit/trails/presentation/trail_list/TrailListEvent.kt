package com.example.hikeit.trails.presentation.trail_list

import com.example.hikeit.core.domain.util.NetworkError

sealed interface TrailListEvent {

    data class Error(val error: NetworkError) : TrailListEvent

}