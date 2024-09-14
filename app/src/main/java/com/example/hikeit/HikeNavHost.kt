package com.example.hikeit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hikeit.data.LocationDetails
import com.example.hikeit.ui.navigate.NavigateScreen
import com.example.hikeit.ui.profile.ProfileScreen
import com.example.hikeit.ui.saved.SavedScreen
import com.example.hikeit.ui.search.SearchScreen
import com.example.hikeit.ui.start.StartScreen

@Composable
fun HikeNavHost(
    navHostController: NavHostController,
    locationDetails: LocationDetails,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navHostController,
        startDestination = Search.route,
        modifier = modifier
    ) {
        composable(route = Search.route) {
            SearchScreen()
        }
        composable(route = Navigate.route) {
            NavigateScreen(locationDetails)
        }
        composable(route = Start.route) {
            StartScreen()
        }
        composable(route = Saved.route) {
            SavedScreen()
        }
        composable(route = Profile.route) {
            ProfileScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
