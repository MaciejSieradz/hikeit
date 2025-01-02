package com.example.hikeit.trails.presentation.saved_trail_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hikeit.core.presentation.util.LoadingWheel
import com.example.hikeit.trails.presentation.trail_list.components.TrailCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedTrailListRoute(
    viewModel: SavedTrailListViewModel = koinViewModel<SavedTrailListViewModel>(),
    onTrailClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SavedTrailListScreen(state, onTrailClick, modifier)
}

@Composable
fun SavedTrailListScreen(
    uiState: SavedTrailListState,
    navigateToTrail: (String) -> Unit,
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
            Text(
                "Zapisane wycieczki",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            if (uiState.trails.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        "Nie masz jeszcze zapisanych wycieczek!",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn {
                    items(items = uiState.trails, key = { trail -> trail.id }) { trail ->
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