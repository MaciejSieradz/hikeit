package com.example.hikeit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.hikeit.ui.theme.HikeItTheme

class HikeActivity : ComponentActivity() {
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
                    topBar = {
                        SearchBar(
                            query = text,
                            onQueryChange = { text = it },
                            onSearch = {
                                active = false
                            },
                            active = active,
                            onActiveChange = {
                                active = it
                            },
                            placeholder = { Text(text = "Wyszukaj Szlaki") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search"
                                )
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        ) {
                            LazyColumn(
                            ) {
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
                        }
                    },
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
                    HikeNavHost(
                        navHostController = navController,
                        modifier = Modifier.padding(innerPadding)
                            .padding(top = 8.dp)
                    )
                    Column(modifier = Modifier.padding(innerPadding)) {

                    }
                }
            }
        }
    }
}