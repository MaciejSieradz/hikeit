package com.example.hikeit.trails.presentation.create_trail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class CreateTrailViewModel() : ViewModel() {

    private val _state = MutableStateFlow(CreateTrailState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CreateTrailState()
        )

    fun onAction(action: CreateTrailAction) {
        when (action) {
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
                    estimatedTimeError = action.time.isBlank()
                )
            }
        }
    }
}