package com.example.hikeit.trails.presentation.create_trail

sealed interface CreateTrailAction {

    data class TitleChanged(val title: String) : CreateTrailAction
    data class DifficultyChanged(val difficulty: String) : CreateTrailAction
    data class DescriptionChanged(val description: String) : CreateTrailAction
    data class EstimatedTimeChanged(val estimatedTime: String) : CreateTrailAction

    data class ValidateTitle(val title: String) : CreateTrailAction
    data class ValidateDifficulty(val difficulty: String) : CreateTrailAction
    data class ValidateDescription(val description: String) : CreateTrailAction
    data class ValidateEstimatedTime(val time: String) : CreateTrailAction
}