package com.example.hikeit.trails.presentation.viewmodels

import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.domain.Comment
import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.TrailDetails
import com.example.hikeit.trails.domain.TrailRepository
import com.example.hikeit.trails.presentation.models.toTrailDetailsUi
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailViewmodel
import com.example.hikeit.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TrailDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var trailRepository: TrailRepository

    private lateinit var viewModel: TrailDetailViewmodel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = TrailDetailViewmodel("1", trailRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun uiState_whenInitialized_thenShowTrailDetail() = runTest {
        Mockito.`when`(trailRepository.getTrailDetails("1")).thenReturn(
            Result.Success(trailDetail)
        )

        backgroundScope.launch(UnconfinedTestDispatcher((testScheduler))) {
            viewModel.state.collect()
        }

        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(trailDetail.toTrailDetailsUi(), viewModel.state.value.trailDetails)
    }
}

val trailDetail = TrailDetails(
    id = "1",
    trailPhotoUrl = "https://tatromaniak.pl/wp-content/uploads/2015/07/szpiglasowy_rafal_ociepka_360_kopia2.jpg",
    title = "Szpiglasowy Wierch do Doliny pięciu stawów",
    difficulty = "Zaawansowany",
    rating = 4.8,
    numberOfRatings = 156,
    distance = 24.0,
    elevationGain = 3071,
    estimatedHikingTime = EstimatedHikingTime(8, 14),
    maxHeight = 2177,
    description = "Piękny szlak zaczynający się przy Morskim Oku, z którego przechodzimy do" +
            "wejścia na Szpiglasowy Wierch, z którego rozpościera się jeden z najpiękniejszych " +
            "widoków na polskie Tatry.",
    comments = listOf(
        Comment(
            userAvatarUrl = "123",
            username = "Maciej Sieradz",
            rating = 4,
            commentDate = "1 miesiąc temu",
            comment =
            "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                    "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!"
        ),
        Comment(
            userAvatarUrl = "123",
            username = "Maciej Sieradz",
            rating = 4,
            commentDate = "1 miesiąc temu",
            comment =
            "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                    "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!"
        ),
        Comment(
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