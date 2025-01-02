package com.example.hikeit.trails.presentation.trail_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hikeit.core.presentation.util.LoadingWheel
import com.example.hikeit.trails.presentation.trail_detail.components.TrailDetail
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrailDetailRoute(
    viewModel: TrailDetailViewmodel = koinViewModel<TrailDetailViewmodel>(),
    onAction: (TrailDetailAction) -> Unit,
    onStarClicked: (Int) -> Unit,
    onFabClicked: (List<LatLng>) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    TrailDetailScreen(
        uiState = uiState,
        onAction = onAction,
        onStarClicked = onStarClicked,
        onFabClicked = onFabClicked,
        modifier = modifier
    )
}

@Composable
fun TrailDetailScreen(
    uiState: TrailDetailState,
    onAction: (TrailDetailAction) -> Unit,
    onStarClicked: (Int) -> Unit,
    onFabClicked: (List<LatLng>) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ExtendedFloatingActionButton(
            onClick = {
                onFabClicked(uiState.trailDetails!!.route.trail.points.map {
                    LatLng(
                        it.latitude,
                        it.longitude
                    )
                })
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .zIndex(2f),
            icon = { Icon(Icons.Default.Hiking, "Hiking Button") },
            text = { Text("Rozpocznij wędrówkę", style = MaterialTheme.typography.titleSmall) }
        )
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(256.dp)
                    )

                    TrailDetail(
                        trailDetailUi = it,
                        comments = uiState.comments,
                        onStarClicked = onStarClicked,
                        onMarkClicked = { isMarked ->
                            onAction(TrailDetailAction.ChangeSaveBookmark(isMarked))
                        },
                        modifier = Modifier
                            .zIndex(1F)
                            .offset(y = (-16).dp)
                    )
                }
            }
        }
    }
}