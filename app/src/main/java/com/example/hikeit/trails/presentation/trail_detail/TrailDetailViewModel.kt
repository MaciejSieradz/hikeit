package com.example.hikeit.trails.presentation.trail_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikeit.core.domain.util.onError
import com.example.hikeit.core.domain.util.onSuccess
import com.example.hikeit.trails.domain.TrailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrailViewModel(
    private val trailId: String,
    private val trailRepository: TrailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TrailDetailState())
    val state = _state
        .onStart { loadTrailDetails(trailId) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            TrailDetailState()
        )

    private fun loadTrailDetails(trailId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            trailRepository.getTrailDetails(trailId)
                .onSuccess { trailDetails ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            trailDetails = trailDetails
                        )
                    }
                }
                .onError {

                }
        }
    }

}