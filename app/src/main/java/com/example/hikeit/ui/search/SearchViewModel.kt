package com.example.hikeit.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikeit.data.TrailInfo
import com.example.hikeit.data.remote.TrailApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    trailApi: TrailApi
) : ViewModel() {

    val uiState: StateFlow<SearchUiState> = trailApi.getAllTrails().map {
        SearchUiState.Trails(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = SearchUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}

sealed interface SearchUiState {
    data object Loading : SearchUiState

    data class Trails(val trails: List<TrailInfo>) : SearchUiState
}