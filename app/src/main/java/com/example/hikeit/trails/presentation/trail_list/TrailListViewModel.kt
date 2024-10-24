package com.example.hikeit.trails.presentation.trail_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikeit.core.domain.util.onError
import com.example.hikeit.core.domain.util.onSuccess
import com.example.hikeit.trails.domain.TrailRepository
import com.example.hikeit.trails.presentation.models.toTrailUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrailListViewModel(
    private val trailRepository: TrailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TrailListState())
    val state = _state
        .onStart { loadTrails() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            TrailListState()
        )

    private val _events = Channel<TrailListEvent>()
    val events = _events.receiveAsFlow()

    private fun loadTrails() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            trailRepository.getTrails()
                .onSuccess { trails ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            trails = trails.map { trail -> trail.toTrailUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(TrailListEvent.Error(error = error))
                }
        }
    }
}