package com.example.hikeit.ui.search

//@RunWith(RobolectricTestRunner::class)
//class SearchViewModelTest {
//
//    @get:Rule
//    val mainDispatcherRule = MainDispatcherRule()
//
//    private val trailDataRepository = TestTrailDataRepository()
//
//    private lateinit var viewModel: SearchViewModel
//
//    @Before
//    fun setup() {
//        viewModel = SearchViewModel(
//            trailDataRepository = trailDataRepository
//        )
//    }
//
//    @Test
//    fun uiState_whenInitialized_thenShowLoading() = runTest {
//        assertEquals(SearchUiState.Loading, viewModel.uiState.value)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun uiState_whenTrailsAreLoading_thenShowLoading() = runTest {
//        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
//
//        assertEquals(SearchUiState.Loading, viewModel.uiState.value)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun uiState_whenTrailLoaded_thenShowTrails() = runTest {
//        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
//
//        trailDataRepository.setTrailData(trailUis)
//
//        assertEquals(
//            SearchUiState.Trails(trails = trailUis),
//            viewModel.uiState.value
//        )
//    }
//}
//
//class TestTrailDataRepository : TrailDataRepository {
//    private val _trailData = MutableSharedFlow<List<TrailUi>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
//
//    override val trailData: Flow<List<TrailUi>> = _trailData.filterNotNull()
//
//    fun setTrailData(trailData: List<TrailUi>) {
//        _trailData.tryEmit(trailData)
//    }
//
//    override suspend fun getTrailDetails(trialId: String): TrailDetails {
//        return TrailDetails()
//    }
//}
//
//val trailUis = listOf(
//    TrailUi(
//        "https://tatromaniak.pl/wp-content/uploads/2015/07/szpiglasowy_rafal_ociepka_360_kopia2.jpg",
//        "Szpiglasowy Wierch do Doliny Pięciu Stawów",
//        "Tatry",
//        4.8,
//        "Zaawansowany",
//        24.0,
//        8,
//        14
//    ),
//    TrailUi(
//        "https://www.e-wczasy.pl/blog/wp-content/uploads/2020/10/szlak-tatry.jpg",
//        "Wołowiec od Zawoi",
//        "Tatry Zachodnie",
//        5.0,
//        "Umiarkowany",
//        12.0,
//        5,
//        3
//    ),
//    TrailUi(
//        "https://www.e-horyzont.pl/media/mageplaza/blog/post/r/y/rysy-01.jpg",
//        "Rysy od polskiej strony",
//        "Tatry",
//        4.8,
//        "Zaawansowany",
//        18.0,
//        6,
//        40
//    )
//)