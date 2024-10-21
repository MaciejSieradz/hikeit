package com.example.hikeit.trails.presentation.trail_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hikeit.trails.presentation.models.TrailUi
import com.example.hikeit.ui.theme.HikeItTheme

@Composable
fun TrailCard(
    trailUi: TrailUi,
    modifier: Modifier = Modifier,
    navigateToTrail: (String) -> Unit
) {
    Column(modifier = modifier) {
        Card(
            Modifier
                .aspectRatio(1.5f)
                .fillMaxWidth()
                .clickable(onClick = { navigateToTrail(trailUi.id) })
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(trailUi.photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = trailUi.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = trailUi.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = trailUi.mountains,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = trailUi.trailAdditionalInfo,
                style = MaterialTheme.typography.labelMedium.copy(),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TrailCardPreview() {
    HikeItTheme {
        TrailCard(
            trailUi = trailUi,
            navigateToTrail = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}

internal val trailUi = TrailUi(
    "",
    "https://tatromaniak.pl/wp-content/uploads/2015/07/szpiglasowy_rafal_ociepka_360_kopia2.jpg",
    "Szpiglasowy Wierch do Doliny Pięciu Stawów",
    "Tatry",
    "4.8 - Zaawansowany - 24.0km - Szac. 8 godz. 14 min"
)