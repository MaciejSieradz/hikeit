package com.example.hikeit.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hikeit.R
import com.example.hikeit.data.TrailInfo
import com.example.hikeit.ui.theme.HikeItTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {

    val photos = listOf(
        TrailInfo(
            stringResource(id = R.string.szpiglasowy_wierch),
            "Szpiglasowy Wierch do Doliny Pięciu Stawów",
            "Tatry",
            4.8,
            "Zaawansowany",
            24.0,
            8,
            14
        ),
        TrailInfo(
            stringResource(id = R.string.tatry_url),
            "Wołowiec od Zawoi",
            "Tatry Zachodnie",
            5.0,
            "Umiarkowany",
            12.0,
            5,
            3
        ),
        TrailInfo(
            stringResource(id = R.string.rysy),
            "Rysy od polskiej strony",
            "Tatry",
            4.8,
            "Zaawansowany",
            18.0,
            6,
            40
        )
    )

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .semantics { contentDescription = "Search Screen" }
    ) {
        items(items = photos, key = { photo -> photo.title }) { photo ->
            TrailCard(
                trailInfo = photo,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }

}

@Composable
fun TrailCard(
    trailInfo: TrailInfo,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Card(
            Modifier
                .aspectRatio(1.5f)
                .fillMaxWidth()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(trailInfo.photo)
                    .crossfade(true)
                    .build(),
                contentDescription = "something",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = trailInfo.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            text = trailInfo.mountains,
            style = MaterialTheme.typography.labelMedium
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
                text = "${trailInfo.stars} - ${trailInfo.difficulty} - ${trailInfo.distance}km - Szac. ${trailInfo.hours} godz. ${trailInfo.minutes} min",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }

}

@Composable
@Preview
fun SearchScreenPreview() {
    HikeItTheme {
        SearchScreen()
    }
}