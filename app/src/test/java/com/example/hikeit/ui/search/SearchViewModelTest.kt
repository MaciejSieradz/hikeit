package com.example.hikeit.ui.search

import com.example.hikeit.data.TrailInfo
import com.example.hikeit.data.remote.TrailApi
import com.example.hikeit.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val trailApi = TestTrailApi()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel(
            trailApi
        )
    }

    @Test
    fun uiState_whenInitialized_thenShowLoading() = runTest {
        assertEquals(SearchUiState.Loading, viewModel.uiState.value)
    }
}

class TestTrailApi : TrailApi {
    override fun getAllTrails(): Flow<List<TrailInfo>> {
         val trails = listOf(
            TrailInfo(
                "https://tatromaniak.pl/wp-content/uploads/2015/07/szpiglasowy_rafal_ociepka_360_kopia2.jpg",
                "Szpiglasowy Wierch do Doliny Pięciu Stawów",
                "Tatry",
                4.8,
                "Zaawansowany",
                24.0,
                8,
                14
            ),
            TrailInfo(
                "https://www.e-wczasy.pl/blog/wp-content/uploads/2020/10/szlak-tatry.jpg",
                "Wołowiec od Zawoi",
                "Tatry Zachodnie",
                5.0,
                "Umiarkowany",
                12.0,
                5,
                3
            ),
            TrailInfo(
                "https://www.e-horyzont.pl/media/mageplaza/blog/post/r/y/rysy-01.jpg",
                "Rysy od polskiej strony",
                "Tatry",
                4.8,
                "Zaawansowany",
                18.0,
                6,
                40
            )
        )
        return flow {
            emit(trails)
        }
    }

}