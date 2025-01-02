package com.example.hikeit.trails.presentation.trail_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hikeit.R
import com.example.hikeit.trails.presentation.models.TrailDetailUi

@Composable
fun TrailStatisticSection(
    trailDetailUi: TrailDetailUi,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(end = 128.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TrailStatistic(
                trailDetailUi.distance, stringResource(
                    R.string.trail_distance
                )
            )
            TrailStatistic(
                trailDetailUi.elevationGain, stringResource(
                    R.string.trail_elevation
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TrailStatistic(
                trailDetailUi.estimatedHikingTime, stringResource(
                    R.string.trail_time
                )
            )
            TrailStatistic(
                trailDetailUi.maxHeight, stringResource(
                    R.string.trail_max_height
                )
            )
        }
    }
}

@Composable
fun TrailStatistic(number: String, description: String) {
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
        }
        Text(
            text = description,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
//
//@PreviewLightDark
//@Composable
//fun TrailStatisticSectionPreview() {
//    HikeItTheme {
//        TrailStatisticSection(
//            trailDetailUi = trailDetailsPreview
//        )
//    }
//}