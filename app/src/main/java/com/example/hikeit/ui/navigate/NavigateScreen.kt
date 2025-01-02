package com.example.hikeit.ui.navigate

import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.hikeit.trails.data.security.AppState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch


@Composable
fun NavigateScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var isLocationPermissionGranted by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState()

    var isTrailPresent by rememberSaveable { mutableStateOf(!AppState.trail.isNullOrEmpty()) }

    val properties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.TERRAIN
            )
        )
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        isLocationPermissionGranted =
            permissions.values.reduce { acc, isGranted -> acc && isGranted }
        if (isLocationPermissionGranted) {
            getUserLocation(context, fusedLocationClient) { location ->
                userLocation = location
                scope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newCameraPosition(
                            CameraPosition.fromLatLngZoom(location, 15f)
                        )
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isTrailPresent) {
            ExtendedFloatingActionButton(
                onClick = {
                    AppState.trail = null
                    isTrailPresent = false
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .zIndex(2f),
                icon = { Icon(Icons.Default.Clear, "Erase trail icon") },
                text = { Text("Wyczyść trasę", style = MaterialTheme.typography.titleSmall) }

            )
        }
        GoogleMap(
            contentPadding = PaddingValues(top = 650.dp),
            modifier = Modifier.fillMaxSize(),
            properties = properties,
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = true,
                compassEnabled = true,
                zoomControlsEnabled = true
            ),
            onMyLocationButtonClick = {
                scope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newCameraPosition(
                            CameraPosition.fromLatLngZoom(userLocation!!, 20f)
                        )
                    )
                }
                true
            }
        ) {
            AppState.trail?.let {
                Polyline(
                    points = it,
                    color = Color.Blue,
                    visible = isTrailPresent
                )
            }
        }
    }
}

private fun getUserLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationResult: (LatLng) -> Unit
) {
    try {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location: Location? ->
            location?.let {
                onLocationResult(LatLng(it.latitude, it.longitude))
            }
        }
    } catch (e: SecurityException) {
        Toast.makeText(context, "Wystąpił problem z uprawnieniami!", Toast.LENGTH_SHORT).show()
    }
}