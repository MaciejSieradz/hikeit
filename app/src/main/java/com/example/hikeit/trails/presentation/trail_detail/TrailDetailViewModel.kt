package com.example.hikeit.trails.presentation.trail_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikeit.core.domain.util.onError
import com.example.hikeit.core.domain.util.onSuccess
import com.example.hikeit.trails.domain.TrailRepository
import com.example.hikeit.trails.presentation.models.toTrailDetailsUi
import com.example.hikeit.trails.presentation.models.toUserCommentUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrailDetailViewmodel(
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

    private val _events = Channel<TrailDetailEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: TrailDetailAction) {
        when (action) {
            is TrailDetailAction.ChangeSaveBookmark -> {
                viewModelScope.launch {
                    if (!action.isMarked) {
                        trailRepository.markTrailAsSaved(trailId)
                            .onSuccess {
                                _state.value = _state.value.copy(
                                    trailDetails = _state.value.trailDetails!!.copy(isMarked = it)
                                )
                                _events.send(TrailDetailEvent.MarkedTrail)
                            }
                            .onError { error ->
                                _events.send(TrailDetailEvent.Error(error))
                            }
                    } else {
                        trailRepository.unmarkTrail(trailId)
                            .onSuccess {
                                _state.value = _state.value.copy(
                                    trailDetails = _state.value.trailDetails!!.copy(isMarked = it)
                                )
                                _events.send(TrailDetailEvent.UnmarkedTrail)
                            }
                            .onError { error ->
                                _events.send(TrailDetailEvent.Error(error))
                            }
                    }
                }
            }
        }
    }

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
                            trailDetails = trailDetails.toTrailDetailsUi(),
                            comments = trailDetails.comments.map { comment -> comment.toUserCommentUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _events.send(TrailDetailEvent.Error(error))
                }
        }
    }

}