package com.example.hikeit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hikeit.ui.theme.HikeItTheme
import org.koin.compose.KoinContext

class HikeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            HikeItTheme {
                KoinContext {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                            AnimatedVisibility(
                                visible = currentRoute in listOf("search", "saved", "navigate"),
                                enter = slideInVertically(initialOffsetY = { it }),
                                exit = slideOutVertically(targetOffsetY = { it })
                            ) {
                                NavigationBar {
                                    hikeTabRowScreens.forEach { destination ->
                                        NavigationBarItem(
                                            icon = {
                                                Icon(
                                                    imageVector = destination.icon,
                                                    contentDescription = destination.route
                                                )
                                            },
                                            label = { Text(destination.name) },
                                            selected = currentRoute == destination.route,
                                            onClick = {
                                                navController.navigateSingleTopTo(destination.route)
                                            }
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets(0, 0, 0, 0)
                    ) { innerPadding ->
                        HikeNavHost(
                            navHostController = navController,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}