package com.example.hikeit.trails.presentation.add_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikeit.core.domain.util.onSuccess
import com.example.hikeit.trails.domain.TrailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddReviewViewModel(
    private val trailId: String,
    private val mark: Int,
    private val trailRepository: TrailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddReviewState(mark))
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AddReviewState(mark)
        )

    fun onAction(action: AddReviewAction) {
        when(action) {

            is AddReviewAction.MarkChanged -> {
                _state.value = _state.value.copy(rating = action.mark)
            }

            is AddReviewAction.PhotosSelected -> {
                _state.value = _state.value.copy(
                    photosUri = action.photosUri,
                    photos = action.photos
                )
            }

            is AddReviewAction.RemovePhoto -> {
                _state.value = _state.value.copy(
                    photosUri = _state.value.photosUri.minus(action.photoUri)
                )
            }

            is AddReviewAction.DescriptionChanged -> {
                _state.value = _state.value.copy(
                    description = action.description
                )
            }

            AddReviewAction.AddReview -> {
                viewModelScope.launch {
                    trailRepository.addReview(trailId, review = _state.value.createReview())
                        .onSuccess {

                        }
                }
            }
        }
    }
}