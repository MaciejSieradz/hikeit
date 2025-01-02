package com.example.hikeit.trails.presentation.trail_detail

sealed interface TrailDetailAction {
    data class ChangeSaveBookmark(val isMarked: Boolean) : TrailDetailAction
}