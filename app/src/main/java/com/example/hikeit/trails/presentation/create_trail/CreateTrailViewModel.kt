package com.example.hikeit.trails.presentation.create_trail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikeit.core.domain.util.onSuccess
import com.example.hikeit.trails.domain.TrailRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.RoundingMode
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class CreateTrailViewModel(
    private val trailRepository: TrailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateTrailState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CreateTrailState()
        )

    fun onAction(action: CreateTrailAction) {
        when (action) {

            is CreateTrailAction.CreateTrail -> {
                viewModelScope.launch {
                    trailRepository.saveTrail(_state.value.createTrailForm())
                        .onSuccess {

                        }
                }
            }

            is CreateTrailAction.TitleChanged -> {
                _state.value = _state.value.copy(
                    title = action.title
                )
            }

            is CreateTrailAction.DifficultyChanged -> {
                _state.value = _state.value.copy(
                    difficulty = action.difficulty
                )
            }

            is CreateTrailAction.DescriptionChanged -> {
                _state.value = _state.value.copy(
                    description = action.description
                )
            }

            is CreateTrailAction.EstimatedTimeChanged -> {
                _state.value = _state.value.copy(
                    estimatedTime = action.estimatedTime
                )
            }

            is CreateTrailAction.GpxFileSelected -> {

                viewModelScope.launch {
                    val points = action.route.trail.points
                    var elevation = 0
                    var negativeElevation = 0
                    var distance = 0.0
                    var maxElevation = points[0].elevation

                    val latitudes = points.mapIndexed { index, point ->
                        if (index != 0) {
                            val elevationDiff = point.elevation - points[index - 1].elevation
                            distance += haversine(
                                point.latitude,
                                point.longitude,
                                points[index - 1].latitude,
                                points[index - 1].longitude
                            )
                            if (elevationDiff > 1) {
                                elevation += elevationDiff
                            }
                            if (elevationDiff < -1) {
                                negativeElevation += -elevationDiff
                            }
                            maxElevation = max(maxElevation, point.elevation)
                        }

                        LatLng(point.latitude, point.longitude)
                    }

                    _state.value = _state.value.copy(
                        gpx = action.gpx,
                        points = latitudes,
                        elevation = elevation,
                        negativeElevation = negativeElevation,
                        maxElevation = maxElevation,
                        distance = distance.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
                            .toDouble()
                    )
                }
            }

            is CreateTrailAction.PhotosSelected -> {
                _state.value = _state.value.copy(
                    photosUri = action.photosUri,
                    photos = action.photos
                )
            }

            is CreateTrailAction.RemovePhoto -> {
                _state.value = _state.value.copy(
                    photosUri = _state.value.photosUri.minus(action.photoUri)
                )
            }

            is CreateTrailAction.ValidateTitle -> {
                _state.value = _state.value.copy(
                    titleError = action.title.isBlank()
                )
            }

            is CreateTrailAction.ValidateDescription -> {
                _state.value = _state.value.copy(
                    descriptionError = action.description.isBlank()
                )
            }

            is CreateTrailAction.ValidateDifficulty -> {
                _state.value = _state.value.copy(
                    difficultyError = action.difficulty.isBlank()
                )
            }

            is CreateTrailAction.ValidateEstimatedTime -> {
                _state.value = _state.value.copy(
                    estimatedTimeError = action.time.hours == 0 && action.time.minutes == 0
                )
            }
        }
    }
}

// Haversine function is a function that counts the distance between two points on a sphere
private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {

    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val lat1Rad = Math.toRadians(lat1)
    val lat2Rad = Math.toRadians(lat2)

    val a = sin(dLat / 2).pow(2) +
            sin(dLon / 2).pow(2) *
            cos(lat1Rad) *
            cos(lat2Rad)

    val rad = 6371 // Earth radius
    val c = 2 * asin(sqrt(a))
    return rad * c
}