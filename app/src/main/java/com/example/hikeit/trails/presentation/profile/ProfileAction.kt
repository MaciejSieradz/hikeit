package com.example.hikeit.trails.presentation.profile

sealed interface ProfileAction {
    data class RemoveTrail(val id: String) : ProfileAction
}