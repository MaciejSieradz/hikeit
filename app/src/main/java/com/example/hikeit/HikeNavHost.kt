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
import com.example.hikeit.trails.data.security.AppState
import com.example.hikeit.trails.presentation.add_review.AddReviewRoute
import com.example.hikeit.trails.presentation.add_review.AddReviewViewModel
import com.example.hikeit.trails.presentation.create_trail.CreateTrailRoute
import com.example.hikeit.trails.presentation.create_trail.CreateTrailViewModel
import com.example.hikeit.trails.presentation.login.LoginScreen
import com.example.hikeit.trails.presentation.profile.ProfileRoute
import com.example.hikeit.trails.presentation.profile.ProfileViewModel
import com.example.hikeit.trails.presentation.saved_trail_list.SavedTrailListRoute
import com.example.hikeit.trails.presentation.saved_trail_list.SavedTrailListViewModel
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailEvent
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailRoute
import com.example.hikeit.trails.presentation.trail_detail.TrailDetailViewmodel
import com.example.hikeit.trails.presentation.trail_list.TrailListEvent
import com.example.hikeit.trails.presentation.trail_list.TrailListRoute
import com.example.hikeit.trails.presentation.trail_list.TrailListViewModel
import com.example.hikeit.ui.navigate.NavigateScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HikeNavHost(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navHostController,
        startDestination = Login.route,
        modifier = modifier
    ) {
        composable(
            route = Login.route
        ) {
            LoginScreen(onSuccessLogin = {
                navHostController.navigate(Search.route) {
                    popUpTo(Login.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = Search.route
        ) {
            val viewModel = koinViewModel<TrailListViewModel>()
            val context = LocalContext.current
            ObserveAsEvents(events = viewModel.events) { event ->
                when (event) {
                    is TrailListEvent.Error -> {
                        Toast.makeText(
                            context,
                            event.error.toString(context),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            TrailListRoute(
                viewModel = viewModel,
                onTrailClick = { trailId ->
                    navHostController.navigateToTrail(trailId)
                },
                onProfileClick = {
                    navHostController.navigateSingleTopTo(Profile.route)
                },
                onFabClick = {
                    navHostController.navigateSingleTopTo(CreateTrail.route)
                }
            )
        }
        composable(
            route = CreateTrail.route
        ) {
            val viewModel = koinViewModel<CreateTrailViewModel>()
            CreateTrailRoute(
                viewModel,
                viewModel::onAction
            )
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
                when (event) {

                    is TrailDetailEvent.Error -> {
                        Toast.makeText(
                            context,
                            event.error.toString(context),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is TrailDetailEvent.MarkedTrail -> {
                        Toast.makeText(
                            context,
                            "Pomyślnie zapisano wycieczkę!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is TrailDetailEvent.UnmarkedTrail -> {
                        Toast.makeText(
                            context,
                            "Pomyślnie usunięto wycieczkę z zapisanych.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            TrailDetailRoute(
                viewModel = viewModel,
                onAction = viewModel::onAction,
                onStarClicked = { mark ->
                    navHostController.navigateToCreateReview(trailId!!, mark)
                },
                onFabClicked = {
                    AppState.trail = it
                    navHostController.navigateSingleTopTo("navigate")
                }
            )
        }
        composable(
            route = "${CreateReview.route}/{${CreateReview.trailId}}/{${CreateReview.mark}}",
            arguments = CreateReview.arguments
        ) { navBackStackEntry ->
            val trailId = navBackStackEntry.arguments?.getString(CreateReview.trailId)!!
            val mark = navBackStackEntry.arguments?.getInt(CreateReview.mark)!!

            val viewModel = koinViewModel<AddReviewViewModel> {
                parametersOf(trailId, mark)
            }
            val context = LocalContext.current
            AddReviewRoute(
                viewModel = viewModel,
                onAction = viewModel::onAction,
                onDiscardButton = { navHostController.popBackStack() })
        }
        composable(route = Navigate.route) {
            NavigateScreen()
        }
        composable(route = Saved.route) {
            val viewModel = koinViewModel<SavedTrailListViewModel>()
            val context = LocalContext.current
            ObserveAsEvents(events = viewModel.events) { event ->
                when (event) {
                    else -> {}
                }
            }
            SavedTrailListRoute(
                viewModel = viewModel,
                onTrailClick = { trailId -> navHostController.navigateToTrail(trailId) }
            )
        }
        composable(route = Profile.route) {
            val viewModel = koinViewModel<ProfileViewModel>()
            ProfileRoute(
                viewModel = viewModel,
                onTrailClick = { trailId -> navHostController.navigateToTrail(trailId) }
            )
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

private fun NavHostController.navigateToCreateReview(trailId: String, mark: Int) {
    this.navigateSingleTopTo("${CreateReview.route}/$trailId/$mark")
}