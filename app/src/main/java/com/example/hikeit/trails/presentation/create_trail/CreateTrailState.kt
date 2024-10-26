package com.example.hikeit.trails.presentation.create_trail

data class CreateTrailState(
    val title: String = "",
    val difficulty: String = "",
    val description: String = "",
    val estimatedTime: String = "",
    val titleError: Boolean = false,
    val difficultyError: Boolean = false,
    val descriptionError: Boolean = false,
    val estimatedTimeError: Boolean = false
)
