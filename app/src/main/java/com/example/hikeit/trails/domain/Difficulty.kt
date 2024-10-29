package com.example.hikeit.trails.domain

enum class Difficulty(val difficultyName: String) {
    HARD("Zaawansowany"),
    MEDIUM("Umiarkowany"),
    EASY("Początkujący");

    companion object {
        fun difficultyFromDifficultyName(difficultyName: String): Difficulty? {
            return Difficulty.entries.firstOrNull { it.difficultyName == difficultyName }
        }
    }
}