package com.example.hikeit.trails.presentation.trail_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hikeit.R
import com.example.hikeit.ui.common.LoadingWheel
import com.example.hikeit.ui.theme.HikeItTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
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

        if(uiState.isLoading) {
            LoadingWheel(
                modifier = modifier,
                contentDesc = "Loading data"
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_broken_image),
                contentDescription = "Trail Photo",
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth

            )
            TrailDetails(
                modifier = Modifier
                    .zIndex(1F)
                    .offset(y = (-16).dp)
            )
        }
    }
}

@Composable
fun TrailDetails(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.large)
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Text(
            text = "Szpiglasowy Wierch do Doliny pięciu stawów",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Zaawansowany - ",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "4.8 (156)",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Column(
            modifier = Modifier
                .padding(end = 128.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TrailDetail("24,0", "km", "Długość szlaku")
                TrailDetail("3071", "m", "Przewyższenie")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TrailDetail("14", "min", "Średni czas przejścia")
                TrailDetail("2177", "m", "Max. wysokość")
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.large)
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.large
                    ),
                cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(LatLng(52.3288, 21.0656), 10F)
                },
                uiSettings = MapUiSettings(
                    scrollGesturesEnabled = false,
                    zoomControlsEnabled = false
                )
            )
        }
        Text("Test")
    }
}

@Composable
fun TrailDetail(number: String, unit: String, description: String) {
    Column {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = unit,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = description,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
@Preview
fun TrailScreenPreview() {
    HikeItTheme {
        TrailDetailScreen(
            uiState = TrailDetailState()
        )
    }
}


@Composable
@Preview
fun TrailInfoScreenPreview() {
    HikeItTheme {
        TrailDetails()
    }
}