package com.example.hikeit

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.hikeit.data.LocationDetails
import com.example.hikeit.ui.theme.HikeItTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration

class HikeActivity : ComponentActivity() {

    private var locationCallback: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequired = false

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HikeItTheme {
                val navController = rememberNavController()

                var text by rememberSaveable {
                    mutableStateOf("")
                }
                var active by rememberSaveable {
                    mutableStateOf(false)
                }
                val countriesList = listOf("Polska", "Czechy", "Niemcy")

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
                    modifier = Modifier.fillMaxSize()
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

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        val colors1 = SearchBarDefaults.colors()
                        SearchBar(
                            inputField = {
                                SearchBarDefaults.InputField(
                                    query = text,
                                    onQueryChange = { text = it },
                                    onSearch = {
                                        active = false
                                    },
                                    expanded = active,
                                    onExpandedChange = { active = it },
                                    enabled = true,
                                    placeholder = { Text(text = "Wyszukaj Szlaki") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "search"
                                        )
                                    },
                                    trailingIcon = null,
                                    colors = colors1.inputFieldColors,
                                    interactionSource = null,
                                )
                            },
                            expanded = active,
                            onExpandedChange = { active = it },
                            modifier = Modifier
                                .padding(vertical = 0.dp),
                            shape = SearchBarDefaults.inputFieldShape,
                            colors = colors1,
                            tonalElevation = SearchBarDefaults.TonalElevation,
                            shadowElevation = SearchBarDefaults.ShadowElevation,
                            windowInsets = SearchBarDefaults.windowInsets,
                            content = {
                                LazyColumn {
                                    items(countriesList) { country ->
                                        Text(
                                            text = country,
                                            modifier = Modifier.padding(
                                                start = 8.dp,
                                                top = 4.dp,
                                                end = 8.dp,
                                                bottom = 4.dp
                                            )
                                        )
                                    }
                                }
                            },
                        )

                        HikeNavHost(
                            navHostController = navController,
                            currentLocation,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }


                Configuration.getInstance().userAgentValue = "hikr"
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