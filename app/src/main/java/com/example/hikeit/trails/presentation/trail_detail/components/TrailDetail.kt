package com.example.hikeit.trails.presentation.trail_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.hikeit.trails.presentation.models.TrailDetailUi
import com.example.hikeit.trails.presentation.models.UserCommentUi
import com.example.hikeit.trails.presentation.trail_detail.trailDetailsPreview
import com.example.hikeit.ui.theme.HikeItTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TrailDetail(
    trailDetailUi: TrailDetailUi,
    comments: List<UserCommentUi>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.large)
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Text(
            text = trailDetailUi.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = trailDetailUi.difficulty,
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
                text = trailDetailUi.rating,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )
        TrailStatisticSection(trailDetailUi)
        Spacer(
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = trailDetailUi.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
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
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Recenzje",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = trailDetailUi.rating,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Column  {
            comments.forEach { comment ->
                CommentCard(comment)
                Spacer(modifier = Modifier.padding(bottom = 8.dp))
            }
        }
    }
}

@Composable
@PreviewLightDark
fun TrailInfoScreenPreview() {
    HikeItTheme {
        TrailDetail(trailDetailsPreview, comments)
    }
}

internal val comments =
    listOf(
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