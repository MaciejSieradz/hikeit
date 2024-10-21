package com.example.hikeit.trails.presentation.trail_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikeit.core.domain.util.onError
import com.example.hikeit.core.domain.util.onSuccess
import com.example.hikeit.trails.domain.TrailRepository
import com.example.hikeit.trails.presentation.models.UserCommentUi
import com.example.hikeit.trails.presentation.models.toTrailDetailsUi
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
                    Log.d("Hiker", "Loading trailDetails for trail: $trailId")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            trailDetails = trailDetails.toTrailDetailsUi(),
                            comments = listOf(
                                UserCommentUi(
                                    userAvatarUrl = "123",
                                    username = "Maciej Sieradz",
                                    rating = 4,
                                    commentDate = "1 miesiąc temu",
                                    comment =
                                    "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                                            "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!"
                                ),
                                UserCommentUi(
                                    userAvatarUrl = "123",
                                    username = "Maciej Sieradz",
                                    rating = 4,
                                    commentDate = "1 miesiąc temu",
                                    comment =
                                    "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                                            "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!"
                                ),
                                UserCommentUi(
                                    userAvatarUrl = "123",
                                    username = "Maciej Sieradz",
                                    rating = 4,
                                    commentDate = "1 miesiąc temu",
                                    comment =
                                    "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                                            "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!"
                                )
                            )
                        )
                    }
                }
                .onError {

                }
        }
    }

}