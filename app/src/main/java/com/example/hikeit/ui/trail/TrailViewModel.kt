package com.example.hikeit.ui.trail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikeit.data.model.TrailDetails
import com.example.hikeit.data.repository.TrailDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrailViewModel @Inject constructor(
    private val trailDataRepository: TrailDataRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TrailViewState())
    val state: StateFlow<TrailViewState> = _state.asStateFlow()

    fun handleIntent(intent: TrailIntent) {

        viewModelScope.launch {
            when (intent) {

                is TrailIntent.LoadTrailDetails -> fetchTrailDetails(trailId = intent.trailId)

            }
        }
    }

    private suspend fun fetchTrailDetails(trailId: String) {
        _state.value = _state.value.copy(loading = true)
        val trailDetails = trailDataRepository.getTrailDetails(trailId)
        _state.value = _state.value.copy(loading = false, trail = trailDetails)
    }

}

data class TrailViewState(
    val loading: Boolean = false,
    val trail: TrailDetails? = null,
)


sealed interface TrailIntent {
    data class LoadTrailDetails(val trailId: String) : TrailIntent
}