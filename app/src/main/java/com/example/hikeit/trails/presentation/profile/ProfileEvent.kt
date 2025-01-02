package com.example.hikeit.trails.presentation.profile

sealed interface ProfileEvent {
    data object RemovedTrail: ProfileEvent
}