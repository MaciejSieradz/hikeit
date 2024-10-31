package com.example.hikeit.trails.presentation.create_trail

import android.net.Uri
import com.example.hikeit.core.data.xml.Route
import com.example.hikeit.trails.domain.Difficulty
import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.TrailForm

data class CreateTrailState(
    val title: String = "",
    val difficulty: String = "",
    val description: String = "",
    val estimatedTime: EstimatedHikingTime? = null ,
    val gpxUri: Uri? = null,
    val route: Route? = null,
    val photosUri: List<Uri> = emptyList(),
    val titleError: Boolean = false,
    val difficultyError: Boolean = false,
    val descriptionError: Boolean = false,
    val estimatedTimeError: Boolean = false
)

fun CreateTrailState.createTrailForm() : TrailForm {
    return TrailForm(
        title = title,
        difficulty = Difficulty.difficultyFromDifficultyName(difficulty)!!,
        description = description,
        estimatedHikingTime = estimatedTime!!,
        gpxUri = gpxUri!!,
        photosUri = photosUri
    )
}
