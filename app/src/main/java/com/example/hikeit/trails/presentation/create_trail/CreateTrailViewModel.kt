package com.example.hikeit.trails.presentation.create_trail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikeit.core.domain.util.onSuccess
import com.example.hikeit.trails.domain.TrailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
                _state.value = _state.value.copy(
                    gpxUri = action.gpxUri
                )
            }

            is CreateTrailAction.PhotosSelected -> {
                _state.value = _state.value.copy(
                    photosUri = action.photosUri
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