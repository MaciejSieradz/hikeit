package com.example.hikeit.trails.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hikeit.core.presentation.util.LoadingWheel
import com.example.hikeit.trails.data.security.AppState
import com.example.hikeit.trails.presentation.trail_list.components.TrailCard
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = koinViewModel<ProfileViewModel>(),
    onAction: (ProfileAction) -> Unit,
    onTrailClick: (String) -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileScreen(state, onAction, onTrailClick, onLogoutClick, modifier)
}

@Composable
fun ProfileScreen(
    uiState: ProfileState,
    onAction: (ProfileAction) -> Unit,
    navigateToTrail: (String) -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (uiState.isLoading) {
            LoadingWheel(
                modifier = modifier,
                contentDesc = "Loading data"
            )
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(AppState.user!!.avatarUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "User avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )

                    Text(
                        text = AppState.user!!.name!!,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = AppState.user!!.email, style = MaterialTheme.typography.labelMedium)
                }
                LogoutButton(navigateToLogin = navigateToLogin)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                "Utworzone wycieczki",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            if (uiState.trails.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        "Nie utworzyłeś jeszcze żadnych wycieczek!",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn {
                    items(items = uiState.trails, key = { trail -> trail.id }) { trail ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            var expanded by remember { mutableStateOf(false) }
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.TopEnd)
                                    .zIndex(2f)
                            ) {
                                IconButton(
                                    onClick = { expanded = !expanded }, modifier =
                                    Modifier
                                        .background(Color.White, CircleShape)
                                        .size(32.dp)
                                ) {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = "More options"
                                    )
                                }
                                val openAlertDialog = remember { mutableStateOf(false) }
                                when {
                                    openAlertDialog.value -> {
                                        DeleteDialog(
                                            onDismissRequest = { openAlertDialog.value = false },
                                            onConfirmation = {
                                                openAlertDialog.value = false
                                                onAction(ProfileAction.RemoveTrail(trail.id))
                                            },
                                            "Usunięcie wycieczki",
                                            "Czy na pewno chcesz usunąć wycieczkę?",
                                            icon = Icons.Default.Info
                                        )
                                    }
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Usuń") },
                                        onClick = {
                                            openAlertDialog.value = true
                                        }
                                    )
                                }
                            }
                            TrailCard(
                                trailUi = trail,
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .semantics { contentDescription = "TrailCard" },
                                navigateToTrail
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Potwierdź")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Anuluj")
            }
        }
    )
}

@Composable
fun LogoutButton(navigateToLogin: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val logout: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        coroutineScope.launch {
            credentialManager.clearCredentialState(ClearCredentialStateRequest())

            navigateToLogin()
        }
    }
    IconButton(onClick = logout) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Logout,
            contentDescription = "Logout button",
            tint = MaterialTheme.colorScheme.error
        )
    }
}