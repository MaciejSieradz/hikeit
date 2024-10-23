package com.example.hikeit

import com.example.hikeit.core.domain.util.Result
import com.example.hikeit.trails.data.mappers.toTrail
import com.example.hikeit.trails.data.networking.dto.EstimatedHikingTimeDto
import com.example.hikeit.trails.data.networking.dto.TrailDto
import com.example.hikeit.trails.domain.TrailRepository
import com.example.hikeit.trails.presentation.models.toTrailUi
import com.example.hikeit.trails.presentation.trail_list.TrailListViewModel
import com.example.hikeit.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TrailListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var trailDataRepository: TrailRepository

    private lateinit var viewModel: TrailListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = TrailListViewModel(trailDataRepository)
    }

    @Test
    fun uiState_whenInitialized_thenShowTrails() = runTest {

        Mockito.`when`(trailDataRepository.getTrails()).thenReturn(
            Result.Success(trails)
        )

        assertEquals(true, viewModel.state.value.isLoading)
        println(viewModel.state.value)
        assertEquals(trails.map { it.toTrailUi() }, viewModel.state.value.trails)
    }

}

val trails = listOf(
    TrailDto(
        "1",
        "https://tatromaniak.pl/wp-content/uploads/2015/07/szpiglasowy_rafal_ociepka_360_kopia2.jpg",
        "Szpiglasowy Wierch do Doliny Pięciu Stawów",
        "Tatry",
        4.8,
        "Zaawansowany",
        24.0,
        EstimatedHikingTimeDto(8, 14)
    ),
    TrailDto(
        "2",
        "https://www.e-wczasy.pl/blog/wp-content/uploads/2020/10/szlak-tatry.jpg",
        "Wołowiec od Zawoi",
        "Tatry Zachodnie",
        5.0,
        "Umiarkowany",
        12.0,
        EstimatedHikingTimeDto(5, 30)
    ),
    TrailDto(
        "3",
        "https://www.e-horyzont.pl/media/mageplaza/blog/post/r/y/rysy-01.jpg",
        "Rysy od polskiej strony",
        "Tatry",
        4.8,
        "Zaawansowany",
        18.0,
        EstimatedHikingTimeDto(6, 40)
    )).map { it.toTrail() }