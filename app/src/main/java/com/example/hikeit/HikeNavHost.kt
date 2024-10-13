package com.example.hikeit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hikeit.data.LocationDetails
import com.example.hikeit.ui.navigate.NavigateScreen
import com.example.hikeit.ui.profile.ProfileScreen
import com.example.hikeit.ui.saved.SavedScreen
import com.example.hikeit.ui.search.SearchRoute
import com.example.hikeit.ui.search.SearchViewModel
import com.example.hikeit.ui.start.StartScreen
import com.example.hikeit.ui.trail.TrailIntent
import com.example.hikeit.ui.trail.TrailRoute
import com.example.hikeit.ui.trail.TrailViewModel

@Composable
fun HikeNavHost(
    navHostController: NavHostController = rememberNavController(),
    locationDetails: LocationDetails,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navHostController,
        startDestination = Search.route,
        modifier = modifier
    ) {
        composable(
            route = Search.route
        ) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchRoute(viewModel) { trailId ->
                navHostController.navigateToTrail(trailId)
            }
        }
        composable(
            route = "${TrailInfo.route}/{${TrailInfo.trailId}}",
            arguments = TrailInfo.arguments
        ) { navBackStackEntry ->
            val trailId =
                navBackStackEntry.arguments?.getString(TrailInfo.trailId)
            val viewModel = hiltViewModel<TrailViewModel>()
            viewModel.handleIntent(TrailIntent.LoadTrailDetails(trailId!!))
            TrailRoute(viewModel)
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

private fun NavHostController.navigateToTrail(trailId: String) {
    this.navigateSingleTopTo("${TrailInfo.route}/$trailId")
}