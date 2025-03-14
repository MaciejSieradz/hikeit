package com.example.hikeit.trails.presentation.saved_trail_list

import android.util.Log
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

class SavedTrailListViewModel(
    private val trailRepository: TrailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SavedTrailListState())
    val state = _state
        .onStart { loadTrails() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SavedTrailListState()
        )

    private val _events = Channel<SavedTrailListEvent>()
    val events = _events.receiveAsFlow()

    private fun loadTrails() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            trailRepository.getSavedTrails()
                .onSuccess { trails ->
                    Log.d("Saved trails","Saved trails: $trails")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            trails = trails.map { trail -> trail.toTrailUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }
}