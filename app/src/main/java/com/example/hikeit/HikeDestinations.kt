package com.example.hikeit

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface HikeDestination {
    val route: String
}

interface HikeMenu : HikeDestination {
    val icon: ImageVector
    val name: String
}

object Search : HikeMenu {
    override val icon = Icons.Filled.Search
    override val route = "search"
    override val name = "Szukaj"
}

object CreateTrail : HikeMenu {
    override val icon = Icons.Filled.Add
    override val route = "add_trail"
    override val name = "Dodaj szlak"
}

object CreateReview : HikeDestination {
    override val route: String = "add_review"
    const val trailId = "trailId"
    const val mark = "mark"
    val arguments = listOf(
        navArgument(trailId) { type = NavType.StringType },
        navArgument(mark) { type = NavType.IntType}
    )
}

object TrailInfo : HikeMenu {
    override val icon = Icons.Filled.Search
    override val route = "trail"
    override val name = "Trail Info"
    const val trailId = "trailId"
    val arguments = listOf(
        navArgument(trailId) { type = NavType.StringType }
    )
}

object Navigate : HikeMenu {
    override val icon = Icons.Filled.Hiking
    override val route = "navigate"
    override val name = "Rozpocznij"

}

object Saved : HikeMenu {
    override val icon = Icons.Filled.Bookmark
    override val route = "saved"
    override val name = "Zapisane"
}

object Profile : HikeMenu {
    override val icon = Icons.Filled.AccountCircle
    override val route = "account"
    override val name = "Profil"
}

object Login : HikeDestination {
    override val route: String = "login"
}

val hikeTabRowScreens = listOf(Search, Navigate, Saved)