package com.example.hikeit.trails.presentation.create_trail

import android.net.Uri
import com.example.hikeit.core.data.xml.Route
import com.example.hikeit.trails.domain.EstimatedHikingTime

sealed interface CreateTrailAction {

    data class TitleChanged(val title: String) : CreateTrailAction
    data class DifficultyChanged(val difficulty: String) : CreateTrailAction
    data class DescriptionChanged(val description: String) : CreateTrailAction
    data class EstimatedTimeChanged(val estimatedTime: EstimatedHikingTime) : CreateTrailAction
    data class GpxFileSelected(val gpxUri: Uri, val route: Route) : CreateTrailAction
    data class PhotosSelected(val photosUri: List<Uri>) : CreateTrailAction
    data class RemovePhoto(val photoUri: Uri) : CreateTrailAction

    data class ValidateTitle(val title: String) : CreateTrailAction
    data class ValidateDifficulty(val difficulty: String) : CreateTrailAction
    data class ValidateDescription(val description: String) : CreateTrailAction
    data class ValidateEstimatedTime(val time: EstimatedHikingTime) : CreateTrailAction

    data object CreateTrail : CreateTrailAction
}