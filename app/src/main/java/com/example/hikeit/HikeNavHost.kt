package com.example.hikeit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hikeit.data.LocationDetails
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailRoute
import com.example.hikeit.trails.presentation.trail_detail.TrailViewModel
import com.example.hikeit.trails.presentation.trail_list.TrailListRoute
import com.example.hikeit.trails.presentation.trail_list.TrailListViewModel
import com.example.hikeit.ui.navigate.NavigateScreen
import com.example.hikeit.ui.profile.ProfileScreen
import com.example.hikeit.ui.saved.SavedScreen
import com.example.hikeit.ui.start.StartScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
            val viewModel = koinViewModel<TrailListViewModel>()
            TrailListRoute(viewModel) { trailId ->
                navHostController.navigateToTrail(trailId)
            }
        }
        composable(
            route = "${TrailInfo.route}/{${TrailInfo.trailId}}",
            arguments = TrailInfo.arguments
        ) { navBackStackEntry ->
            val trailId =
                navBackStackEntry.arguments?.getString(TrailInfo.trailId)
            val viewModel = koinViewModel<TrailViewModel> {
                parametersOf(trailId)
            }
            TrailDetailRoute(viewModel)
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