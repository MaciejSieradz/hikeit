package com.example.hikeit

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.hikeit.data.LocationDetails
import com.example.hikeit.ui.theme.HikeItTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.koin.compose.KoinContext

class HikeActivity : ComponentActivity() {

    private var locationCallback: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            HikeItTheme {
                KoinContext {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                hikeTabRowScreens.forEach { destination ->
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                destination.icon,
                                                contentDescription = destination.route
                                            )
                                        },
                                        label = { Text(destination.name) },
                                        selected = false,
                                        onClick = {
                                            navController.navigateSingleTopTo(destination.route)
                                        }
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets(0, 0, 0, 0)
                    ) { innerPadding ->

                        var currentLocation by remember {
                            mutableStateOf(LocationDetails(49.toDouble(), 51.toDouble()))
                        }

                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                        locationCallback = object : LocationCallback() {
                            override fun onLocationResult(p0: LocationResult) {
                                for (lo in p0.locations) {
                                    currentLocation = LocationDetails(lo.latitude, lo.longitude)
                                }
                            }
                        }

                        startLocationUpdates()

                        HikeNavHost(
                            navHostController = navController,
                            currentLocation,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = LocationRequest.Builder(100, 1000).build()
            Log.i("Hikr", "New location!")
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }
}