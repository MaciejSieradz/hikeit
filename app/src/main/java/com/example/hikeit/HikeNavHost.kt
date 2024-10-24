package com.example.hikeit

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hikeit.core.presentation.util.ObserveAsEvents
import com.example.hikeit.core.presentation.util.toString
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailEvent
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailRoute
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailViewmodel
import com.example.hikeit.trails.presentation.trail_list.TrailListEvent
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
            val context = LocalContext.current
            ObserveAsEvents(events = viewModel.events) { event ->
                when(event) {
                    is TrailListEvent.Error -> {
                        Toast.makeText(
                            context,
                            event.error.toString(context),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
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
            val viewModel = koinViewModel<TrailDetailViewmodel> {
                parametersOf(trailId)
            }
            val context = LocalContext.current
            ObserveAsEvents(events = viewModel.events) { event ->
                when(event) {

                    is TrailDetailEvent.Error -> {
                        Toast.makeText(
                            context,
                            event.error.toString(context),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            TrailDetailRoute(viewModel)
        }
        composable(route = Navigate.route) {
            NavigateScreen()
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