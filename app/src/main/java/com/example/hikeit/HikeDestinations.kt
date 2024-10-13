package com.example.hikeit

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface HikeDestination {
    val icon: ImageVector
    val route: String
    val name: String
}

object Search : HikeDestination {
    override val icon = Icons.Filled.Search
    override val route = "search"
    override val name = "Szukaj"
}

object TrailInfo : HikeDestination {
    override val icon = Icons.Filled.Search
    override val route = "trail"
    override val name = "Trail Info"
    const val trailId = "trailId"
    val arguments = listOf(
        navArgument(trailId) { type = NavType.StringType}
    )
}

object Navigate : HikeDestination {
    override val icon = Icons.Filled.LocationOn
    override val route = "navigate"
    override val name = "Nawiguj"

}

object Start : HikeDestination {
    override val icon = Icons.Default.Hiking
    override val route = "start"
    override val name = "Rozpocznij"
}

object Saved : HikeDestination {
    override val icon = Icons.Filled.Bookmark
    override val route = "saved"
    override val name = "Zapisane"
}

object Profile : HikeDestination {
    override val icon = Icons.Filled.AccountCircle
    override val route = "account"
    override val name = "Profil"
}

val hikeTabRowScreens = listOf(Search, Navigate, Start, Saved, Profile)