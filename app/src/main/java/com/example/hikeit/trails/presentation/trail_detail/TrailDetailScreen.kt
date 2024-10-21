package com.example.hikeit.trails.presentation.trail_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hikeit.core.presentation.util.LoadingWheel
import com.example.hikeit.trails.domain.EstimatedHikingTime
import com.example.hikeit.trails.domain.TrailDetails
import com.example.hikeit.trails.presentation.models.toTrailDetailsUi
import com.example.hikeit.trails.presentation.trail_detail.components.TrailDetail
import com.example.hikeit.ui.theme.HikeItTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrailDetailRoute(
    viewModel: TrailViewModel = koinViewModel<TrailViewModel>(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    TrailDetailScreen(uiState = uiState, modifier = modifier)
}

@Composable
fun TrailDetailScreen(
    uiState: TrailDetailState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(state = rememberScrollState())
            .semantics { contentDescription = "Trail Screen" },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (uiState.isLoading) {
            LoadingWheel(
                modifier = modifier,
                contentDesc = "Loading data"
            )
        } else {
            uiState.trailDetails?.let {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(uiState.trailDetails.trailPhotoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = uiState.trailDetails.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                        .height(256.dp)
                )

                TrailDetail(
                    trailDetailUi = it,
                    comments = uiState.comments,
                    modifier = Modifier
                        .zIndex(1F)
                        .offset(y = (-16).dp)
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun TrailScreenPreview() {
    HikeItTheme {
        TrailDetailScreen(
            uiState = TrailDetailState(
                isLoading = false,
                trailDetails = trailDetailsPreview
            )
        )
    }
}

internal val trailDetailsPreview = TrailDetails(
    id = "",
    trailPhotoUrl = "",
    title = "Szpiglasowy Wierch do Doliny pięciu stawów",
    difficulty = "Zaawansowany",
    rating = 4.8,
    numberOfRatings = 156,
    distance = 24.0,
    elevationGain = 3071,
    estimatedHikingTime = EstimatedHikingTime(6, 40),
    maxHeight = 2177,
    description = "Piękny szlak zaczynający się przy Morskim Oku, z którego przechodzimy do" +
            "wejścia na Szpiglasowy Wierch, z którego rozpościera się jeden z najpiękniejszych " +
            "widoków na polskie Tatry.",
    comments = emptyList()
).toTrailDetailsUi()