package com.example.hikeit.trails.presentation.trail_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hikeit.trails.presentation.models.TrailDetailUi
import com.example.hikeit.trails.presentation.models.UserCommentUi
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TrailDetail(
    trailDetailUi: TrailDetailUi,
    comments: List<UserCommentUi>,
    onStarClicked: (Int) -> Unit,
    onMarkClicked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
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
            }
            Icon(
                imageVector = if (trailDetailUi.isMarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Add to bookmark",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onMarkClicked(trailDetailUi.isMarked)
                    },
                tint = MaterialTheme.colorScheme.secondary
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
            text = trailDetailUi.description.replace("\n", ""),
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
                    position = CameraPosition.fromLatLngZoom(
                        LatLng(
                            trailDetailUi.route.trail.points[0].latitude,
                            trailDetailUi.route.trail.points[0].longitude
                        ), 15f
                    )
                },
                uiSettings = MapUiSettings(
                    scrollGesturesEnabled = false,
                    zoomControlsEnabled = false
                )
            ) {
                Polyline(
                    points = trailDetailUi.route.trail.points.map {
                        LatLng(
                            it.latitude,
                            it.longitude
                        )
                    },
                    color = Color.Blue
                )
            }
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
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Column {
            Text("Oceń i opisz")
            Row {
                Icon(
                    imageVector = Icons.Default.StarOutline,
                    contentDescription = "Star",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onStarClicked(1)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.Default.StarOutline,
                    contentDescription = "Star",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onStarClicked(2)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.Default.StarOutline,
                    contentDescription = "Star",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onStarClicked(3)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.Default.StarOutline,
                    contentDescription = "Star",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onStarClicked(4)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.Default.StarOutline,
                    contentDescription = "Star",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onStarClicked(5)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Column {
            comments.forEach { comment ->
                CommentCard(comment, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.padding(bottom = 8.dp))
            }
        }
    }
}

//@Composable
//@PreviewLightDark
//fun TrailInfoScreenPreview() {
//    HikeItTheme {
//        TrailDetail(trailDetailsPreview, comments)
//    }
//}

internal val comments =
    listOf(
        UserCommentUi(
            userAvatarUrl = "123",
            username = "Maciej Sieradz",
            rating = 4,
            commentDate = "1 miesiąc temu",
            comment =
            "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                    "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!",
            photosUrl = emptyList()
        ),
        UserCommentUi(
            userAvatarUrl = "123",
            username = "Maciej Sieradz",
            rating = 4,
            commentDate = "1 miesiąc temu",
            comment =
            "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                    "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!",

            photosUrl = emptyList()
        ),
        UserCommentUi(
            userAvatarUrl = "123",
            username = "Maciej Sieradz",
            rating = 4,
            commentDate = "1 miesiąc temu",
            comment =
            "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
                    "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!",

            photosUrl = emptyList()
        )
    )