package com.example.hikeit.trails.presentation.create_trail.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hikeit.core.data.xml.Route
import com.example.hikeit.core.data.xml.parseGpxFile
import com.example.hikeit.trails.presentation.trail_detail.components.TrailStatistic
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AddGpxFile(
    points: List<LatLng>,
    elevation: Int?,
    negativeElevation: Int?,
    distance: Double?,
    maxElevation: Int?,
    onGpxSelected: (ByteArray, Route) -> Unit
) {
    val context = LocalContext.current

    var visible by remember {
        mutableStateOf(false)
    }

    val gpxPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {

        it?.let { uri ->

            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri)

            lateinit var route: Route
            lateinit var contentBytes: ByteArray

            inputStream?.let { stream ->
                contentBytes = stream.readBytes()
                val content = contentBytes.decodeToString()

                route = content.parseGpxFile()
            }

            inputStream?.close()

            onGpxSelected(contentBytes, route)
            visible = true
        }
    }

    Button(
        onClick = {
            gpxPicker.launch("application/gpx+xml")
        }
    ) {
        Text("Wybierz plik z rozszerzeniem GPX")
    }

    val cameraPosition = rememberCameraPositionState()
    if (points.isNotEmpty()) {
        cameraPosition.position = CameraPosition.fromLatLngZoom(
            LatLng(
                points[0].latitude,
                points[0].longitude
            ), 15f
        )
    }

    AnimatedVisibility(
        visible = visible
    ) {
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(shape = MaterialTheme.shapes.large)
            ) {
                GoogleMap(
                    cameraPositionState = cameraPosition,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = MaterialTheme.shapes.large
                        )
                ) {
                    Polyline(
                        points = points,
                        color = Color.Blue
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(end = 128.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TrailStatistic(
                        "${distance.toString().replace('.', ',')} km",
                        "Całkowity dystans"
                    )
                    TrailStatistic(
                        "$maxElevation m",
                        "Najwyższy punkt"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(top = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(100.dp)
                ) {
                    TrailStatistic(
                        "$negativeElevation m",
                        "Suma zejść"
                    )
                    TrailStatistic(
                        "$elevation m",
                        "Suma podejść"
                    )
                }
            }
        }
    }
}