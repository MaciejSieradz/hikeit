package com.example.hikeit.trails.presentation.create_trail.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hikeit.core.data.xml.Route
import com.example.hikeit.core.data.xml.parseGpxFile
import com.gitlab.mvysny.konsumexml.konsumeXml
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AddGpxFile(
    route: Route?,
    onGpxSelected: (Uri, Route) -> Unit
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

            inputStream?.let { stream ->
                val content = stream.reader().readText()
                route = content.parseGpxFile()
            }

            inputStream?.close()

            onGpxSelected(uri, route)
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
    route?.let {
        cameraPosition.position = CameraPosition.fromLatLngZoom(LatLng(route.metadata.bounds.maxLatitude, route.metadata.bounds.maxLongitude), 15f)
    }

    AnimatedVisibility(
        visible = visible
    ) {
        GoogleMap(
            cameraPositionState = cameraPosition,
            modifier = Modifier
                .height(256.dp)
                .padding(16.dp)
        ) {
            route?.let { route ->  route.trail.points.map { LatLng(it.latitude, it.longitude) } }?.let {
                Polyline(
                    points = it,
                    color = Color.Blue
                )
            }
        }
    }
}