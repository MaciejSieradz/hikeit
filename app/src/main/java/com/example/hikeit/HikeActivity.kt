package com.example.hikeit

import android.os.Bundle
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
import androidx.compose.ui.Modifier
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